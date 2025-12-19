package com.aiService.aiService.service;

import com.aiService.aiService.client.MlModelClient;
import com.aiService.aiService.dto.priorityRequestDto;
import com.aiService.aiService.dto.priorityResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class priorityService {
    private final MlModelClient mlModelClient;

    public priorityResponseDto predictPriority(priorityRequestDto dto){
        priorityResponseDto response =  mlModelClient.callModel(dto.getComplaintText());
        response.setId(dto.getId());
        return response;
    }

}
