package com.user.user.service;

import com.user.user.dto.*;
import com.user.user.exception.UserNotFoundException;
import com.user.user.model.OfficerDetailsProjection;
import com.user.user.model.OfficerModel;
import com.user.user.model.OfficerStatus;
import com.user.user.model.userModel;
import com.user.user.repository.officerRepository;
import com.user.user.repository.userRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class userService {
    @Autowired
    private userRepository userRepository;
    @Autowired
    private officerRepository officerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<responseDto> getAllUsers(){
        List<userModel> userModelList = userRepository.findAll();
        return modelMapper.map(userModelList, new TypeToken<List<responseDto>>() {}.getType());
    }
    public responseDto getUserById(UUID id){
        userModel user = userRepository.getById(id);
        return modelMapper.map(user, responseDto.class);
    }
    public List<responseDto> getUserByName(String name){
        List<userModel> users = userRepository.getByName(name);
        if(users.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        else {
            return modelMapper.map(users,new TypeToken<List<responseDto>>(){}.getType());
        }
    }
    public responseDto getUserByPhone(String phoneNumber){
        userModel user = userRepository.getByPhone(phoneNumber);
        System.out.println(user.getCreateAT());
        System.out.println(user.getUpdateAT());
        return modelMapper.map(user,responseDto.class);
    }
    public responseDto saveUser(responseDto dto){
        UUID userId = dto.getId();
        userModel user = userRepository.findById(userId).orElse(null);
        if (user == null){
            userRepository.save(modelMapper.map(dto, userModel.class));
            if("officer".equalsIgnoreCase(dto.getRole())){
                syncOfficerTable(dto.getId());
            }
            System.out.println("user added");
            return dto;
        }else {
            user.setFullName(dto.getFullName());
            user.setEmail(dto.getEmail());
            user.setPhoneNumber( dto.getPhoneNumber());
        }
        user.setUpdateAT(LocalDateTime.now());
        userModel savedUser = userRepository.save(user);
        System.out.println("user exist");

        return modelMapper.map(savedUser,responseDto.class);
    }
    public String updateUser(requestDto requestDto){
        try {
            UUID id =  requestDto.getId();
            userModel user = userRepository.getById(id);
            user.setFullName(requestDto.getFullName());
            user.setEmail(requestDto.getEmail());
            user.setPhoneNumber(requestDto.getPhoneNumber());
            user.setUpdateAT(LocalDateTime.now());
            userRepository.save(user);
            return "Profile updated successfully";
        }catch (Exception e){
            return "Error : "+ e.getMessage();
        }
    }
    public String deleteUserById(UUID id){
         userRepository.deleteById(id);
         return "User deleted successfully";
    }
    //return user stats
    public userStatsDto getUserStats(){
        userStatsDto userStats = new userStatsDto();
        userStats.setTotalUsers(userRepository.findAll().size());
        userStats.setTotalRegularUsers(userRepository.getRegularUserCount());
        userStats.setTotalAdmins(userRepository.getAdminCount());
        userStats.setTotalOfficers(userRepository.getOfficerCount());
        return userStats;
    }

    // add officer details to officer table
    public boolean syncOfficerTable(UUID id){
        userModel user = userRepository.findById(id).orElse(null);
        if(user == null){
            return false;
        }
        if(!"officer".equalsIgnoreCase(user.getRole())){
            return false;
        }
        OfficerModel OfficerExisting = officerRepository.findByOfficer_Id(id);
        if (OfficerExisting != null){
            return false;
        }
        OfficerModel officer = new OfficerModel();
        officer.setOfficer(user);
        officer.setOfficerStatus(OfficerStatus.ACTIVE);
        officerRepository.save(officer);
        return true;
    }
    // return all officer-model details
    public List<OfficerModel> getOfficers(){
        return officerRepository.findAll();
    }

    // return all officer details
    public List<officerDetailsDto> getOfficersDetails(){
        List<OfficerDetailsProjection> result = officerRepository.getOfficerFullDetails();
        return result.stream().map(r-> new officerDetailsDto(
                r.getId(),
                r.getOfficerId(),
                r.getAccountableDistrict(),
                r.getOfficerStatus(),
                r.getAssignedCasesCount(),
                r.getFullName(),
                r.getPhoneNumber(),
                r.getEmail(),
                r.getCreateAT(),
                r.getUpdateAT()
        )).toList();
    }
    //update officer status and district
    public ResponseEntity<?> updateOfficerDetails(Long Id, officerUpdateDto dto){
        OfficerModel officer = officerRepository.findById(Id).orElse(null);
        if(officer == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        officer.setAccountableDistrict(dto.getAccountableDistrict());
        officer.setOfficerStatus(OfficerStatus.valueOf(dto.getOfficerStatus()));
        officerRepository.save(officer);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    //update officer AssignedCasesCount
    public ResponseEntity<?> UpdateAssignedCasesCount (UUID officerId,String option){
        OfficerModel officer  = officerRepository.findByOfficer_Id(officerId);
        if(officer == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (option.equalsIgnoreCase("DECREMENT")){
            officer.setAssignedCasesCount(officer.getAssignedCasesCount()-1);
            officerRepository.save(officer);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        officer.setAssignedCasesCount(officer.getAssignedCasesCount()+1);
        officerRepository.save(officer);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
