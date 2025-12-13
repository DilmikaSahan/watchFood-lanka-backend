package com.complaintService.complaintService.controller;

import com.complaintService.complaintService.dto.*;
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
    //get complaint by assigned officer
    @GetMapping("/assignedComplaints")
    public List<userCompliantResponseDto> getAssignedComplaints(@AuthenticationPrincipal Jwt jwt){
        UUID officerID = UUID.fromString(jwt.getClaimAsString("sub"));
        return ComplaintService.getCompliantOfficerId(officerID);
    }

    //save new complain, permission : user
    @PostMapping("/addComplaint")
    public userCompliantResponseDto addComplaint(@AuthenticationPrincipal Jwt jwt, @RequestBody userCompliantCreateDto complaint){
        UUID userID = UUID.fromString(jwt.getClaimAsString("sub"));
        return ComplaintService.saveComplaint(complaint,userID);
    }
    //update complaint by user
    @PutMapping("/updateComplainUser")
    public userCompliantResponseDto updateComplaint(@RequestBody userComplaintupdateDto complaint){
        return ComplaintService.updateComplaintUser(complaint);
    }
    // delete compliant by user
    @DeleteMapping("/deleteComplaint/{complaintId}")
    public ResponseEntity<?> deleteComplaint(@PathVariable Long complaintId){
        return ComplaintService.deleteComplaintByComplaintId(complaintId);
    }
    //update images: add new images to compliant
    @PutMapping("/addImages/{complaintId}")
    public List<String> addImages(@PathVariable Long complaintId,@RequestBody List<String> images){
        return ComplaintService.addImages(complaintId,images);
    }
    //update images: remove images from complaint
    @PutMapping("/deleteImages/{complaintId}")
    public List<String> deleteImages(@PathVariable Long complaintId,@RequestBody List<String> images){
        return ComplaintService.deleteImages(complaintId,images);
    }
    //self assign officer to complaint
    @PutMapping("/assignOfficer/{complaintId}")
    public ResponseEntity<?> assignOfficer(@AuthenticationPrincipal Jwt jwt,@PathVariable Long complaintId){
        UUID officerId = UUID.fromString(jwt.getClaimAsString("sub"));
        return ComplaintService.assignedOfficerToCompliant(complaintId,officerId);
    }
    //update complaint officer section
    @PutMapping("/updateOfficerSection/{complaintId}")
    public ResponseEntity<?> updateOfficerSection(@AuthenticationPrincipal Jwt jwt,@PathVariable Long complaintId,@RequestBody OfficerComplaintRequestDto dto){
        UUID officerId = UUID.fromString(jwt.getClaimAsString("sub"));
        return ComplaintService.updateOfficerSection(officerId,complaintId,dto);
    }
    //get complaint stats
    @GetMapping("/getComplaintStats")
    public complaintStatsDto gatComplaintStats(@AuthenticationPrincipal Jwt jwt){
        return ComplaintService.getAllComplaintStats();
    }
    //update officer assignment by admin
    @PutMapping("/updateOfficerAssignment/{complaintId}")
    public ResponseEntity<?> updateOfficerAssigment(@PathVariable Long complaintId){
        return ComplaintService.updateOfficerAssignment(complaintId);
    }


}
