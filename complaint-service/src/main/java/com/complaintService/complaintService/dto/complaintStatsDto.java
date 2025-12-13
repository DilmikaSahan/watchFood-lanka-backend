package com.complaintService.complaintService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class complaintStatsDto {
    private Integer totalComplaints;
    private Integer resolvedComplaints;
    private Integer pendingComplaints;
    private Integer inProgressComplaints;
    private Integer rejectedComplaints;
}
