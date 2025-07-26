package hust.hoangson.user.repository;

import hust.hoangson.user.domain.entity.UserProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<UserProfileEntity, UUID> {
    @Query("SELECT DISTINCT u FROM UserProfileEntity u " +
            "JOIN u.roles r " +
            "JOIN u.addresses ad " +
            "WHERE (:userId IS NULL OR u.userId = :userId) " +
            "AND (:username IS NULL OR u.username = :username) " +
            "AND (:email IS NULL OR u.email LIKE CONCAT('%', :email, '%')) " +
            "AND (:phone IS NULL OR u.phone LIKE CONCAT('%', :phone, '%')) " +
            "AND (:fullname IS NULL OR u.fullName LIKE CONCAT('%', :fullname, '%')) " +
            "AND (:role IS NULL OR r.role = :role) " +
            "AND ad.isDefault = true " +
            "AND u.isActive = true")

    Page<UserProfileEntity> getListUser(@Param("userId") String userId,
                                        @Param("username") String username,
                                        @Param("email") String email,
                                        @Param("phone") String phone,
                                        @Param("fullname") String fullname,
                                        @Param("role") String role,
                                        Pageable pageable);
}
