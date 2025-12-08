package com.complaintService.complaintService.dto;

import com.complaintService.complaintService.model.CompliantStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfficerComplaintRequestDto {
    private UUID officer;
    private String officerNote;
    private CompliantStatus status;
}
