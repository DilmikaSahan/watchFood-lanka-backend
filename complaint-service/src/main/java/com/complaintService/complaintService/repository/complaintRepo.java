package com.complaintService.complaintService.repository;

import com.complaintService.complaintService.model.complaintModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface complaintRepo extends JpaRepository<complaintModel,Long> {
    @Query(value = "SELECT* FROM complaint_model WHERE complaint_id = ?1",nativeQuery = true)
    complaintModel getCompliantById(Long compliantId);

    @Query(value = "SELECT* FROM complaint_model WHERE complainer_id = ?1",nativeQuery = true)
    List<complaintModel> getCompliantByUserId(UUID complainerId);

    @Query(value = "SELECT unnest(string_to_array(image_url, ',')) AS url FROM complaint_model WHERE compliant_id = ?1",nativeQuery = true)
    List<String> getImageUrls(Long complaintId);

    @Query(value = "SELECT * FROM complaint_model WHERE officer = ?1",nativeQuery = true)
    List<complaintModel> getCompliantByOfficer(UUID officer);

    @Query(value = "SELECT COUNT(status) FROM complaint_model WHERE status = 'PENDING'",nativeQuery = true)
    Integer getPendingCompliantCount();

    @Query(value = "SELECT COUNT(status) FROM complaint_model WHERE status = 'UNDER_INVESTIGATION'",nativeQuery = true)
    Integer getInProgressCompliantCount();

    @Query(value = "SELECT COUNT(status) FROM complaint_model WHERE status = 'RESOLVED'",nativeQuery = true)
    Integer getResolvedCompliantCount();

    @Query(value = "SELECT COUNT(status) FROM complaint_model WHERE status = 'REJECTED'",nativeQuery = true)
    Integer getRejectedCompliantCount();


}
