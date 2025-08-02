package hust.hoangson.auth.controller;

import hust.hoangson.auth.repository.UserRepository;
import hust.hoangson.auth.request.LoginRequest;
import hust.hoangson.auth.request.RegisterRequest;
import hust.hoangson.auth.response.BaseResponse;
import hust.hoangson.auth.service.AuthService;
import hust.hoangson.auth.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(BaseResponse.success(authService.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(BaseResponse.success(authService.login(request)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody String request) {
        
        return ResponseEntity.ok(BaseResponse.success(refreshTokenService.refreshAccessToken(request)));
    }
}
