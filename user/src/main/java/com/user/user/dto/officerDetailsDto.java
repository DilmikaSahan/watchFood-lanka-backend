package com.user.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class officerDetailsDto {
    Long id;
    UUID officerId;
    String accountableDistrict;
    String officerStatus;
    Integer assignedCasesCount;
    String fullName;
    String email;
    String phoneNumber;
    LocalDateTime createAT;
    LocalDateTime updateAT;
}
