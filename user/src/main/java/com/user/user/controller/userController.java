package com.user.user.controller;

import com.user.user.dto.*;
import com.user.user.model.userModel;
import com.user.user.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/user")
public class userController {
    @Autowired
    private userService userService;

    @GetMapping("/allusers")
    public List<responseDto> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/findUserById")
    public responseDto getUserById(@RequestParam("id") UUID id) {
        return userService.getUserById(id);
    }

    @GetMapping("/findUserByName")
    public List<responseDto> getAllUsersByName(@RequestParam String name){
        return userService.getUserByName(name);
    }
    @GetMapping("/findUserByPhoneNumber")
    public responseDto getUserByPhone(@RequestParam String phonenumber){
        return userService.getUserByPhone(phonenumber);
    }


    @PostMapping("/syncUser")
    public responseDto saveRegularUser(@AuthenticationPrincipal Jwt jwt){
        responseDto user = new responseDto();
        //extract user info from the token
        user.setId(UUID.fromString(jwt.getSubject()));
        user.setEmail(jwt.getClaims().get("email").toString());
        user.setFullName(jwt.getClaims().get("name").toString());
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");

        // extract role from the token
        if (realmAccess != null && realmAccess.get("roles") != null) {
            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) realmAccess.get("roles");
            String userRole = "user";
            if(roles.contains("admin")) {
                userRole = "admin";
            }else if(roles.contains("officer")) {
                userRole = "officer";
            }else if(roles.contains("user")) {
                userRole = "user";
            }
            user.setRole(userRole);
        }else {
            user.setRole("user");
        }

        user.setPhoneNumber(jwt.getClaims().get("phone_number").toString());
        user.setCreateAT(LocalDateTime.now());
        user.setUpdateAT(LocalDateTime.now());
        return userService.saveUser(user);
    }
    @PostMapping("/saveUserByAdminUser")
    public responseDto saveUserByAdmin(@RequestBody requestDto requestDto){
        responseDto user = new responseDto();
        user.setId(requestDto.getId());
        user.setFullName(requestDto.getFullName());
        user.setRole(requestDto.getRole());
        user.setEmail(requestDto.getEmail());
        user.setPhoneNumber(requestDto.getPhoneNumber());
        user.setCreateAT(LocalDateTime.now());
        user.setUpdateAT(LocalDateTime.now());
        System.out.println(user);
        return userService.saveUser(user);
    }

    @PutMapping("/updateuser")
    public String updateUser(@RequestBody requestDto requestDto){
        return userService.updateUser(requestDto);
    }
    //get user stats
    @GetMapping("/getUserStats")
    public userStatsDto getUserStats(){
        return userService.getUserStats();
    }
    //get all officer details
    @GetMapping("/getOfficerDetails")
    public List<officerDetailsDto> getOfficerDetails(){
        return userService.getOfficersDetails();
    }
    //update officer details status and accountableDistrict
    @PutMapping("/updateOfficerDetails/{Id}")
    public ResponseEntity<?> updateOfficer(@PathVariable Long Id, @RequestBody officerUpdateDto dto){
        return userService.updateOfficerDetails(Id,dto);

    }
    //update officer AssignedCasesCount
    @PutMapping("/UpdateAssignedCasesCount/{officerId}")
    public ResponseEntity<?> UpdateAssignedCasesCount(@PathVariable UUID officerId,@RequestBody String option){
        return userService.UpdateAssignedCasesCount(officerId,option);
    }


}
