package com.aiService.aiService.controller;

import com.aiService.aiService.dto.PriorityRequest;
import com.aiService.aiService.dto.PriorityResponse;
import com.aiService.aiService.service.priorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.PriorityQueue;

@RestController
@RequestMapping("/api/v1/ai")
public class priorityController {
    @Autowired
    private priorityService PriorityService;

    @PostMapping("/priority")
    public PriorityResponse CallPriorityService(@RequestBody PriorityRequest priorityRequest){
        return PriorityService.predictPriority(priorityRequest);

    }
}
