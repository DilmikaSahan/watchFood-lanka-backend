package com.complaintService.complaintService.service;

import com.complaintService.complaintService.dto.*;
import com.complaintService.complaintService.kafka.kafkaProducer;
import com.complaintService.complaintService.model.CompliantStatus;
import com.complaintService.complaintService.model.complaintModel;
import com.complaintService.complaintService.repository.complaintRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class complaintService {
    @Autowired
    private complaintRepo complaintrepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private kafkaProducer KafkaProducer;

    // get all complaints, access permission : officer, admin
    public List<userCompliantResponseDto> getAllCompliants() {
        List<complaintModel> complaintList = complaintrepo.findAll();
        return modelMapper.map(complaintList,new TypeToken<List<userCompliantResponseDto>>(){}.getType());
    }

    //get all complaints related to the user access permission : all
    public List<userCompliantResponseDto> getCompliantsByUserId(UUID complainerId) {
        List<complaintModel> userCompliantList = complaintrepo.getCompliantByUserId(complainerId);
        if (userCompliantList.isEmpty()) {
            return new ArrayList<>();
        }
        return modelMapper.map(userCompliantList, new TypeToken<List<userCompliantResponseDto>>(){}.getType());
    }

    //get complaint by complaint ID
    public userCompliantResponseDto getCompliantByComplaintId(Long complaintId) {
        complaintModel complaint = complaintrepo.getCompliantById(complaintId);
        return modelMapper.map(complaint,userCompliantResponseDto.class);
    }

    // save complaint
    @Transactional
    public userCompliantResponseDto saveComplaint(userCompliantCreateDto complaint, UUID complainerId) {
        complaintModel complaintmodel = new complaintModel();
        complaintmodel.setComplainerId(complainerId);
        complaintmodel.setComplaintAt(LocalDateTime.now());
        complaintmodel.setStatus(CompliantStatus.PENDING);
        modelMapper.map(complaint,complaintmodel);
        complaintrepo.save(complaintmodel);
        KafkaProducer.sendForPrioritization(new priorityRequestDto(
                complaintmodel.getComplaintId(),complaintmodel.getDescription()
        ));
        return modelMapper.map(complaintmodel,userCompliantResponseDto.class);
    }


    // update complaint user section, access permission : only user
    @Transactional
    public userCompliantResponseDto updateComplaintUser(userComplaintupdateDto complaint){
        Long complaintID = complaint.getComplaintId();
        complaintModel complaintmodel = complaintrepo.getCompliantById(complaintID);
        modelMapper.map(complaint,complaintmodel);
        complaintrepo.save(complaintmodel);
        return  modelMapper.map(complaintmodel,userCompliantResponseDto.class);
    }

    // delete complaint, access permission : only user
    public ResponseEntity<Map<String,String>> deleteComplaintByComplaintId(Long complaintId){
        complaintrepo.deleteById(complaintId);
        return ResponseEntity.ok(Map.of("message","success"));
    }
    //add new images
    @Transactional
    public List<String> addImages(Long complaintID,List<String> images){
        complaintModel complaint = complaintrepo.getCompliantById(complaintID);
        List<String> imagesList = complaint.getImageUrl();
        imagesList.addAll(images);
        complaintrepo.save(complaint);
        return imagesList;
    }
    //delete images
    @Transactional
    public List<String> deleteImages(Long complaintID, List<String> images){
        complaintModel complaint = complaintrepo.getCompliantById(complaintID);
        System.out.println("delete images : "+images);
        List<String> imagesList = complaint.getImageUrl();
        imagesList.removeAll(images);
        complaintrepo.save(complaint);
        return imagesList;
    }
    @Transactional
    //assigned officer to complaint
    public ResponseEntity<Map<String,String>> assignedOfficerToCompliant(Long complaintId,UUID officerId){
        complaintModel complaint = complaintrepo.getCompliantById(complaintId);
        if (complaint.getOfficer()!=null){
            return ResponseEntity.ok(Map.of("message","Officer is already assigned"));
        }
        complaint.setOfficer(officerId);
        complaintrepo.save(complaint);
        return ResponseEntity.ok(Map.of("message","success"));
    }
    //get all complaint by assigned officer ID
    public List<userCompliantResponseDto> getCompliantOfficerId(UUID officerId) {
        List<complaintModel> complaints = complaintrepo.getCompliantByOfficer(officerId);
        if (complaints.isEmpty()) {
            return new ArrayList<>();
        }
        return modelMapper.map(complaints,new TypeToken<List<userCompliantResponseDto>>(){}.getType());
    }
    //update officer section
    @Transactional
    public ResponseEntity<Map<String,String>> updateOfficerSection(UUID officerId,Long complaintID,OfficerComplaintRequestDto dto){
        UUID officer = dto.getOfficer();
        if (!officer.equals(officerId)) {
            return ResponseEntity.status(403).body(Map.of("message","unauthorized"));
        }
        complaintModel complaint = complaintrepo.getCompliantById(complaintID);
        if (complaint == null) {
            return ResponseEntity.status(404).body(Map.of("message","not found"));
        }
        complaint.setStatus(dto.getStatus());
        complaint.setOfficerNote(dto.getOfficerNote());
        complaintrepo.save(complaint);
        return ResponseEntity.ok(Map.of("message","success"));

        }
    //return all complaints stats
    public complaintStatsDto getAllComplaintStats(){
        complaintStatsDto complaintStats = new complaintStatsDto();
        complaintStats.setTotalComplaints(complaintrepo.count());
        complaintStats.setPendingComplaints(complaintrepo.getPendingCompliantCount());
        complaintStats.setResolvedComplaints(complaintrepo.getResolvedCompliantCount());
        complaintStats.setRejectedComplaints(complaintrepo.getRejectedCompliantCount());
        complaintStats.setInProgressComplaints(complaintrepo.getInProgressCompliantCount());
        return complaintStats;
    }
    @Transactional
    // remove (unassign) officer from complain: permission  admin
    public ResponseEntity<?> updateOfficerAssignment(Long complaintID){
        complaintModel complaint = complaintrepo.getCompliantById(complaintID);
        if (complaint == null) {
            return ResponseEntity.status(404).body(Map.of("message","not found"));
        }
        complaint.setOfficer(null);
        complaintrepo.save(complaint);
        return ResponseEntity.ok(Map.of("message","success"));
    }
    //update priority of complaint
    @Transactional
    public ResponseEntity<?> updateComplaintPriority(priorityResponseDto  responseDto){
        complaintModel complaint =  complaintrepo.getCompliantById(responseDto.getId());
        if (complaint == null) {
            return ResponseEntity.status(404).body(Map.of("message","not found"));
        }
        complaint.setPriorityLevel(responseDto.getPriorityLevel());
        complaintrepo.save(complaint);
        return  ResponseEntity.ok(Map.of("message","success"));
    }
    }




