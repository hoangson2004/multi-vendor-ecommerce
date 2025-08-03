package hust.hoangson.auth.service.impl;

import hust.hoangson.auth.domain.entity.UserEntity;
import hust.hoangson.auth.repository.RefreshTokenRepository;
import hust.hoangson.auth.repository.UserRepository;
import hust.hoangson.auth.response.AuthResponse;
import hust.hoangson.auth.service.JwtService;
import hust.hoangson.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public AuthResponse refreshAccessToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);

        if (!jwtService.isTokenValid(refreshToken, username)) {
            return null;
        }

        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return null;
        }

        String accessToken = jwtService.generateAccessToken(user.get());
        return AuthResponse.fromEntity(accessToken, refreshToken);
    }

    @Override
    public void deleteToken(UserEntity user) {
        refreshTokenRepository.deleteByUser(user);
    }

}
