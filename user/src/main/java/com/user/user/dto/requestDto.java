package com.user.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class requestDto {
    private UUID id;
    private String fullName;
    private String role;
    private String email;
    private String district;
    private String phoneNumber;
}

