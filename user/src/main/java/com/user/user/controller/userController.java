package com.user.user.controller;

import com.user.user.dto.requestDto;
import com.user.user.dto.responseDto;
import com.user.user.model.userModel;
import com.user.user.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/user")
public class userController {
    @Autowired
    private userService userService;

    @GetMapping("/allusers")
    public List<responseDto> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/getUserById")
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

    @PostMapping("/saveRegularUser")
    public responseDto saveRegularUser(@RequestBody requestDto requestDto){
        responseDto user = new responseDto();
        user.setId(requestDto.getId());
        user.setFullName(requestDto.getFullName());
        user.setRole("user");
        user.setEmail(requestDto.getEmail());
        user.setDistrict(requestDto.getDistrict());
        user.setPhoneNumber(requestDto.getPhoneNumber());
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
        user.setDistrict(requestDto.getDistrict());
        user.setPhoneNumber(requestDto.getPhoneNumber());
        user.setCreateAT(LocalDateTime.now());
        user.setUpdateAT(LocalDateTime.now());
        return userService.saveUser(user);
    }

    @PutMapping("/updateuser")
    public String updateUser(@RequestBody requestDto requestDto){
        return userService.updateUser(requestDto);
    }

}
