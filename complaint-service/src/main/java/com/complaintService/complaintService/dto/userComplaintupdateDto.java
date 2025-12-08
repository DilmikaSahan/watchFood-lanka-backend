package com.complaintService.complaintService.dto;

import com.complaintService.complaintService.model.ComplaintCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class userComplaintupdateDto {
    private Long complaintId;
    private UUID complainerId;
    private ComplaintCategory category;
    private String description;
    private String province;
    private String district;
    private String city;
    private String location;
    private List<String> imageUrl;
}
