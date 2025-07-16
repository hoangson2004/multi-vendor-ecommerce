package hust.hoangson.auth.service.impl;

import hust.hoangson.auth.domain.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledJobService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Scheduled(cron = "0 0 * * * *")
    public void deleteExpiredTokens() {
        int deletedCount = refreshTokenRepository.deleteAllByExpiresAtBefore(LocalDateTime.now());
        log.info("Cleaned up {} expired refresh tokens", deletedCount);
    }
}
