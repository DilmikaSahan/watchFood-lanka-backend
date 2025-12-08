package com.complaintService.complaintService.controller;

import com.complaintService.complaintService.dto.userCompliantCreateDto;
import com.complaintService.complaintService.dto.userComplaintupdateDto;
import com.complaintService.complaintService.dto.userCompliantResponseDto;
import com.complaintService.complaintService.service.complaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/complaints")
public class complaintsController {
    @Autowired
    private complaintService  ComplaintService;

    //get compliant
    @GetMapping("/allComplaints")
    public List<userCompliantResponseDto> getAllComplaints(){
        return ComplaintService.getAllCompliants();
    }

    //get My complaints by complainerID
    @GetMapping("/myComplaints")
    public List<userCompliantResponseDto> getMyComplaints(@AuthenticationPrincipal Jwt jwt){
        UUID complainerId = UUID.fromString(jwt.getClaimAsString("sub"));
        return ComplaintService.getCompliantsByUserId(complainerId);
    }
    //get complaint by complaint ID
    @GetMapping("/getComplaint/{complaintId}")
    public userCompliantResponseDto getComplaint(@PathVariable Long complaintId){
        return ComplaintService.getCompliantByComplaintId(complaintId);
    }
    //save new complain, permission : user
    @PostMapping("/addComplaint")
    public userCompliantResponseDto addComplaint(@AuthenticationPrincipal Jwt jwt, @RequestBody userCompliantCreateDto complaint){
        UUID userID = UUID.fromString(jwt.getClaimAsString("sub"));
        return ComplaintService.saveComplaint(complaint,userID);
    }
    @PutMapping("/updateComplainUser")
    public userCompliantResponseDto updateComplaint(@RequestBody userComplaintupdateDto complaint){
        return ComplaintService.updateComplaintUser(complaint);
    }
    @DeleteMapping("/deleteComplaint/{complaintId}")
    public ResponseEntity<?> deleteComplaint(@PathVariable Long complaintId){
        return ComplaintService.deleteComplaintByComplaintId(complaintId);
    }

    @PutMapping("/addImages/{complaintId}")
    public List<String> addImages(@PathVariable Long complaintId,@RequestBody List<String> images){
        return ComplaintService.addImages(complaintId,images);
    }

    @PutMapping("/deleteImages/{complaintId}")
    public List<String> deleteImages(@PathVariable Long complaintId,@RequestBody List<String> images){
        return ComplaintService.deleteImages(complaintId,images);
    }

}
