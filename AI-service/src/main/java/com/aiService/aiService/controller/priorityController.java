package com.aiService.aiService.controller;

import com.aiService.aiService.dto.priorityRequestDto;
import com.aiService.aiService.dto.priorityResponseDto;
import com.aiService.aiService.service.priorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai")
public class priorityController {
    @Autowired
    private priorityService PriorityService;

    @PostMapping("/priority")
    public priorityResponseDto CallPriorityService(@RequestBody priorityRequestDto PriorityRequestDto){
        return PriorityService.predictPriority(PriorityRequestDto);

    }
}
