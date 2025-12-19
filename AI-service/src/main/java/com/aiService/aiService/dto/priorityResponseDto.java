package com.aiService.aiService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class priorityResponseDto {
    private String priorityLevel;
    private double confidence;
    private Long id;
}


