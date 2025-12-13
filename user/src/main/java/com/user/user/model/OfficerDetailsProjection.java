package com.user.user.model;

import java.time.LocalDateTime;
import java.util.UUID;

public interface OfficerDetailsProjection {
    Long getId();
    UUID getOfficerId();
    String getAccountableDistrict();
    String getOfficerStatus();
    Integer getAssignedCasesCount();
    String getFullName();
    String getEmail();
    String getPhoneNumber();
    LocalDateTime getCreateAT();
    LocalDateTime getUpdateAT();
}
