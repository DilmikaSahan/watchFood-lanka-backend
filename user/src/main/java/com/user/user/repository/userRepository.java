package com.user.user.repository;

import com.user.user.model.userModel;
import org.springframework.beans.factory.annotation.Value;
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

    @Query(value = "SELECT COUNT(role) FROM user_model WHERE role = 'user'",nativeQuery = true)
    Integer getRegularUserCount();

    @Query(value = "SELECT COUNT(role) FROM user_model WHERE role = 'officer'",nativeQuery = true)
    Integer getOfficerCount();

    @Query(value = "SELECT COUNT(role) FROM user_model WHERE role = 'admin'",nativeQuery = true)
    Integer getAdminCount();

    List<userModel> id(UUID id);

    List<userModel> findAllByRole(String role);

}
