package hust.hoangson.auth.repository;

import hust.hoangson.auth.domain.entity.RefreshTokenEntity;
import hust.hoangson.auth.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity,String> {
    int deleteAllByExpiresAtBefore(LocalDateTime expiresAt);
    void deleteByUser(UserEntity user);
}
