package hust.hoangson.auth.service;

import hust.hoangson.auth.domain.entity.UserEntity;
import hust.hoangson.auth.response.AuthResponse;

public interface RefreshTokenService {
    AuthResponse refreshAccessToken(String refreshToken);
    void deleteToken (UserEntity user);
}
