package hust.hoangson.auth.service;

import hust.hoangson.auth.domain.entity.RefreshToken;
import hust.hoangson.auth.domain.entity.User;
import hust.hoangson.auth.response.AuthResponse;

public interface RefreshTokenService {
    AuthResponse refreshAccessToken(String refreshToken);
    void deleteToken (User user);
}
