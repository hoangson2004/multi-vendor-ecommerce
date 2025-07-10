package hust.hoangson.auth.controller;

import hust.hoangson.auth.request.LoginRequest;
import hust.hoangson.auth.request.RegisterRequest;
import hust.hoangson.auth.response.BaseResponse;
import hust.hoangson.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(BaseResponse.success(authService.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(BaseResponse.success(authService.login(request)));
    }
}
