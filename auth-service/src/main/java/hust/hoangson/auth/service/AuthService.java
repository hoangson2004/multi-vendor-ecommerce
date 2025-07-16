package hust.hoangson.auth.service;

import hust.hoangson.auth.request.LoginRequest;
import hust.hoangson.auth.request.RegisterRequest;
import hust.hoangson.auth.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}