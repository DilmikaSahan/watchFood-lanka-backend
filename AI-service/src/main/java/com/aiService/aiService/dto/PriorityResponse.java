package com.aiService.aiService.dto;

import lombok.Data;

@Data
public class PriorityResponse {
    private String priority;
    private double confidence;
    private Integer id;
}


