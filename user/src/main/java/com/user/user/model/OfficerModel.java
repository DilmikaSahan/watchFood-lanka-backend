package com.user.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfficerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToOne
    @JoinColumn(name="officer_id",referencedColumnName = "id")
    private userModel officer;

    private String accountableDistrict;

    private Integer assignedCasesCount = 0;

    @Enumerated(EnumType.STRING)
    private OfficerStatus officerStatus;

}
