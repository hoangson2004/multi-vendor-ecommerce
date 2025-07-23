package hust.hoangson.user.repository;

import hust.hoangson.user.domain.dto.UserDTO;
import hust.hoangson.user.domain.entity.UserProfile;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, UUID> {
    @Query("SELECT DISTINCT u FROM UserProfile u LEFT JOIN UserRole r ON r.userId = u.userId LEFT JOIN UserAddress ad ON ad.userId = u.userId WHERE" +
            "((:userId IS NULL) OR u.userId = :userId) " +
            "AND (:username IS NULL OR u.username = :username) " +
            "AND (:email IS NULL OR TRIM(LOWER(u.email)) LIKE CONCAT ('%', :email, '%')) " +
            "AND (:phone IS NULL OR TRIM(LOWER(u.phone)) LIKE CONCAT ('%', :phone, '%')) " +
            "AND (:fullname IS NULL OR TRIM((LOWER(u.fullName))) LIKE CONCAT ('%', (LOWER(:fullname)), '%')) " +
            "AND (:role IS NULL OR r.role = :role) " +
            "AND (ad.isDefault = true )")
    List<UserDTO> getListUser(@Param("userId") UUID userId,
                              @Param("username") String username,
                              @Param("email") String email,
                              @Param("phone") String phone,
                              @Param("fullname") String fullname,
                              @Param("role") String role);
}
