package com.complaintService.complaintService.model;

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
public class complaintModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long compliantId;

    @Column(nullable = false)
    private UUID complainerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintCategory category;

    @Column(length = 2000, nullable = false)
    private String description;

    private String imageUrl;

    private LocalDateTime complaintAt;

    private UUID officer;

    private String officerNote;

    @Enumerated(EnumType.STRING)
    private CompliantStatus status;

    private Integer priorityLevel;

}
