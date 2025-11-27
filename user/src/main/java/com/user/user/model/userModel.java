package com.user.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class userModel {
    @Id
    private UUID id;
    private String fullName;
    private String role;
    private String email;
    private String phoneNumber;

    private LocalDateTime createAT = LocalDateTime.now();
    private LocalDateTime updateAT = LocalDateTime.now();

    @PreUpdate
    public void setLastUpdate(){
        this.updateAT = LocalDateTime.now();
    }
}
