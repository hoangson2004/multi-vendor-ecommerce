package hust.hoangson.user.repository;

import hust.hoangson.user.domain.entity.UserAddressEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddressEntity, Long> {

    @Query("SELECT a FROM UserAddressEntity a WHERE a.userId = :userId AND a.isActive = true " +
            "ORDER BY a.isDefault DESC, a.updatedAt DESC")
    Page<UserAddressEntity> findAddressByUserId(@Param("userId") String userId, Pageable pageable);

    @Modifying
    @Query("UPDATE UserAddressEntity a SET a.isDefault = false WHERE a.userId = :userId")
    void resetDefaultAddress(@Param("userId") String userId);

    UserAddressEntity findById(UUID id);
}
