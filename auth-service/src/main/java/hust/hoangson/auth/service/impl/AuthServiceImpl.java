package hust.hoangson.auth.service.impl;

import hust.hoangson.auth.domain.entity.UserEntity;
import hust.hoangson.auth.domain.enums.AuthProvider;
import hust.hoangson.auth.domain.enums.Role;
import hust.hoangson.auth.repository.RefreshTokenRepository;
import hust.hoangson.auth.repository.UserRepository;
import hust.hoangson.auth.exception.*;
import hust.hoangson.auth.messaging.producer.UserEventPublisher;
import hust.hoangson.auth.request.LoginRequest;
import hust.hoangson.auth.request.RegisterRequest;
import hust.hoangson.auth.response.AuthResponse;
import hust.hoangson.auth.service.AuthService;
import hust.hoangson.auth.service.JwtService;
import hust.hoangson.common.kafka.event.user.UserCreatedEvent;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.zip.CRC32;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository  refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserEventPublisher userEventPublisher;

    @Override
    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail()) || userRepository.existsByUsername(req.getUsername())) {
            throw new UserAlreadyExistsException();
        }

        UserEntity user = UserEntity.builder()
                .userId(generateUserId(req.getUsername()))
                .username(req.getUsername())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(Role.USER.getCode())
                .provider(AuthProvider.LOCAL.getCode())
                .build();

        user = userRepository.save(user);

        String token = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        UserCreatedEvent event = UserCreatedEvent.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        userEventPublisher.publishUserCreated(event);

        return AuthResponse.fromEntity(token, refreshToken);
    }

    @Override
    public AuthResponse login(LoginRequest req) {
        Optional<UserEntity> user = userRepository.findByEmail(req.getEmail());
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }

        if (!passwordEncoder.matches(req.getPassword(), user.get().getPassword())) {
            throw new PasswordMismatchException();
        }

        validateLoginAccount(user.get());

        String token = jwtService.generateAccessToken(user.get());
        String refreshToken = jwtService.generateRefreshToken(user.get());

        return AuthResponse.fromEntity(token, refreshToken);
    }

    private void validateLoginAccount(UserEntity user) {
        if (Boolean.TRUE.equals(user.getIsBanned())) {
            throw new AccountBannedException();
        }

        if (Boolean.TRUE.equals(user.getIsLocked())) {
            if (user.getLockedUntil() == null || user.getLockedUntil().isAfter(LocalDateTime.now())) {
                throw new AccountLockedException();
            } else {
                user.setIsLocked(false);
                user.setLockedUntil(null);
                userRepository.save(user);
            }
        }
    }

    public static String generateUserId(String username) {
        String timestamp = String.valueOf(System.currentTimeMillis());

        String userPart = hashTo6Digits(username);

        String timePart = hashTo6Digits(timestamp);

        return "USR" + userPart + timePart;
    }

    private static String hashTo6Digits(String input) {
        CRC32 crc = new CRC32();
        crc.update(input.getBytes());
        long hash = crc.getValue() % 1_000_000;
        return String.format("%06d", hash);
    }

    @Transactional
    public int deleteUser(String userId) {
        return userRepository.deleteUserByUserId(userId);
    }
}
