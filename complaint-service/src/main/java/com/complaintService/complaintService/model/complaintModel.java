package com.complaintService.complaintService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class complaintModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long complaintId;

    @Column(nullable = false)
    private UUID complainerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintCategory category;

    @Column(length = 2000, nullable = false)
    private String description;

    private String province;
    private String district;
    private String city;
    private String location;

    @Column(name = "image_url")
    @JdbcTypeCode(SqlTypes.ARRAY)
    private List<String> imageUrl;

    private LocalDateTime complaintAt;

    private UUID officer;

    private String officerNote;

    @Enumerated(EnumType.STRING)
    private CompliantStatus status;

    private Integer priorityLevel;

}
