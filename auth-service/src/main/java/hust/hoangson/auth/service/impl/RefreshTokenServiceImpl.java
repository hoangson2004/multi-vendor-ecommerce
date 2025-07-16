package hust.hoangson.auth.service.impl;

import hust.hoangson.auth.domain.entity.RefreshToken;
import hust.hoangson.auth.domain.entity.User;
import hust.hoangson.auth.domain.repository.RefreshTokenRepository;
import hust.hoangson.auth.domain.repository.UserRepository;
import hust.hoangson.auth.response.AuthResponse;
import hust.hoangson.auth.service.JwtService;
import hust.hoangson.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return null;
        }

        String accessToken = jwtService.generateAccessToken(user.get());
        return AuthResponse.fromEntity(accessToken, refreshToken);
    }

    @Override
    public void deleteToken(User user) {
        refreshTokenRepository.deleteByUser(user);
    }

}
