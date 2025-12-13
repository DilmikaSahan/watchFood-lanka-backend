package com.user.user.repository;

import com.user.user.model.OfficerDetailsProjection;
import com.user.user.model.OfficerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface officerRepository extends JpaRepository<OfficerModel,Long> {
    OfficerModel findByOfficer_Id(UUID officerId);

    @Query(value = "SELECT o.id AS id, u.id AS officerId,o.accountable_district AS accountableDistrict,o.officer_status AS officerStatus,o.assigned_cases_count AS assignedCasesCount,u.full_name AS fullName,u.email AS email,u.phone_number AS phoneNumber,u.createat AS createAT, u.updateat AS updateAT FROM officer_model o JOIN user_model u ON o.officer_id = u.id ",nativeQuery = true)
    List<OfficerDetailsProjection>  getOfficerFullDetails();
}
