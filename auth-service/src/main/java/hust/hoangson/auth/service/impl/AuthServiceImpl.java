package hust.hoangson.auth.service.impl;

import hust.hoangson.auth.domain.enums.AuthProvider;
import hust.hoangson.auth.domain.enums.Role;
import hust.hoangson.auth.domain.entity.User;
import hust.hoangson.auth.domain.repository.UserRepository;
import hust.hoangson.auth.request.LoginRequest;
import hust.hoangson.auth.request.RegisterRequest;
import hust.hoangson.auth.response.AuthResponse;
import hust.hoangson.auth.service.AuthService;
import hust.hoangson.auth.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            return null;
        }

        User user = User.builder()
                .userRef("USR" + System.currentTimeMillis())
                .username(req.getUsername())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(Role.USER.getCode())
                .provider(AuthProvider.LOCAL.getCode())
                .build();

        user = userRepository.save(user);

        String token = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return AuthResponse.fromEntity(token, refreshToken);
    }

    @Override
    public AuthResponse login(LoginRequest req) {
        Optional<User> user = userRepository.findByEmail(req.getEmail());
        if (!user.isPresent()) {
            return null;
        }

        if (!passwordEncoder.matches(req.getPassword(), user.get().getPassword())) {
            return null;
        }

        String token = jwtService.generateAccessToken(user.get());
        String refreshToken = jwtService.generateRefreshToken(user.get());

        return AuthResponse.fromEntity(token, refreshToken);
    }
}
