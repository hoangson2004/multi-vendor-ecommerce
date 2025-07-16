package hust.hoangson.auth.domain.repository;

import hust.hoangson.auth.domain.entity.RefreshToken;
import hust.hoangson.auth.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,String> {
    int deleteAllByExpiresAtBefore(LocalDateTime expiresAt);
    void deleteByUser(User user);
}
