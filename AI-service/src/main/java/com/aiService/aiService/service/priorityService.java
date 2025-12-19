package com.aiService.aiService.service;

import com.aiService.aiService.client.MlModelClient;
import com.aiService.aiService.dto.PriorityRequest;
import com.aiService.aiService.dto.PriorityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class priorityService {
    private final MlModelClient mlModelClient;

    public PriorityResponse predictPriority(PriorityRequest dto){
        PriorityResponse response =  mlModelClient.callModel(dto.getComplaintText());
        response.setId(dto.getId());
        return response;
    }

}
