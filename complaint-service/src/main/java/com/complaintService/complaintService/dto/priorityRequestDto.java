package com.complaintService.complaintService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class priorityRequestDto {
    private  Long id;
    private  String complaintText;
}


