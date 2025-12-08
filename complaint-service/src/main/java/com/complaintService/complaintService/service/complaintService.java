package com.complaintService.complaintService.service;

import com.complaintService.complaintService.dto.userCompliantResponseDto;
import com.complaintService.complaintService.dto.userCompliantCreateDto;
import com.complaintService.complaintService.dto.userComplaintupdateDto;
import com.complaintService.complaintService.model.complaintModel;
import com.complaintService.complaintService.repository.complaintRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class complaintService {
    @Autowired
    private complaintRepo complaintrepo;

    @Autowired
    private ModelMapper modelMapper;
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
    public userCompliantResponseDto saveComplaint(userCompliantCreateDto complaint, UUID complainerId) {
        complaintModel complaintmodel = new complaintModel();
        complaintmodel.setComplainerId(complainerId);
        complaintmodel.setComplaintAt(LocalDateTime.now());
        modelMapper.map(complaint,complaintmodel);
        complaintrepo.save(complaintmodel);
        return modelMapper.map(complaintmodel,userCompliantResponseDto.class);
    }

    // update complaint user section, access permission : only user
    public userCompliantResponseDto updateComplaintUser(userComplaintupdateDto complaint){
        Long complaintID = complaint.getComplaintId();
        complaintModel complaintmodel = complaintrepo.getCompliantById(complaintID);
        complaintrepo.save(complaintmodel);
        return  modelMapper.map(complaintmodel,userCompliantResponseDto.class);
    }

    // delete complaint, access permission : only user
    public String deleteComplaintUserById(Long complaintID){
        complaintrepo.deleteById(complaintID);
        return "success";
    }


}
