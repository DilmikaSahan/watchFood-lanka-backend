package com.user.user.service;

import com.user.user.dto.requestDto;
import com.user.user.dto.responseDto;
import com.user.user.exception.UserNotFoundException;
import com.user.user.model.userModel;
import com.user.user.repository.userRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class userService {
    @Autowired
    private userRepository userRepository;
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
    public responseDto saveUser(responseDto user){
        userRepository.save(modelMapper.map(user, userModel.class));
        return user;
    }
    public String updateUser(requestDto requestDto){
        try {
            UUID id =  requestDto.getId();
            userModel user = userRepository.getById(id);
            user.setFullName(requestDto.getFullName());
            user.setEmail(requestDto.getEmail());
            user.setDistrict(requestDto.getDistrict());
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

}
