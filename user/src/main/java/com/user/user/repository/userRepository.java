package com.user.user.repository;

import com.user.user.model.userModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface userRepository extends JpaRepository<userModel, UUID> {
    @Query(value = "SELECT *FROM user_model WHERE id =?1",nativeQuery = true)
    userModel getById(UUID id);
    @Query(value = "SELECT *FROM user_model WHERE full_name LIKE %?1%",nativeQuery = true)
    List<userModel> getByName(String name);
    @Query(value = "SELECT *FROM user_model WHERE phone_number = ?1",nativeQuery = true)
    userModel getByPhone(String phonenumber);
}
