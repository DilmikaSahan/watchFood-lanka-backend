package com.complaintService.complaintService.repository;

import com.complaintService.complaintService.model.complaintModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface complaintRepo extends JpaRepository<complaintModel, Long> {
}
