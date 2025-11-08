package com.user.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class responseDto {
    private UUID id;
    private String fullName;
    private String email;
    private String role;
    private String district;
    private String phoneNumber;
    private LocalDateTime createAT;
    private LocalDateTime updateAT;
}
