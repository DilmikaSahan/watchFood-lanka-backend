package com.user.user.service;

import com.user.user.dto.responseDto;
import com.user.user.model.userModel;
import com.user.user.repository.userRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
