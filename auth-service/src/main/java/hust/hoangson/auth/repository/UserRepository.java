package hust.hoangson.auth.repository;

import hust.hoangson.auth.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);

    @Modifying
    @Query("DELETE FROM UserEntity u WHERE u.userId = :userId")
    int deleteUserByUserId(@Param("userId") String userId);
}