package com.user.user.repository;

import com.user.user.model.userModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface userRepository extends JpaRepository<userModel, UUID> {
    @Query(value = "SELECT *FROM userModel WHERE id =?1",nativeQuery = true)
    userModel getById(UUID id);
}
