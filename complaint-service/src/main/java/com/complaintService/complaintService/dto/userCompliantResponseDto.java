package com.complaintService.complaintService.dto;

import com.complaintService.complaintService.model.ComplaintCategory;
import com.complaintService.complaintService.model.CompliantStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class userCompliantResponseDto {
    private UUID complainerId;
    private Long complaintId;
    private ComplaintCategory category;
    private String description;
    private String province;
    private String district;
    private String city;
    private String location;
    private List<String> imageUrl;
    private CompliantStatus  status;
    private UUID officer;
    private String officerNote;
    private LocalDateTime complaintAt;
    private String priorityLevel;

}
