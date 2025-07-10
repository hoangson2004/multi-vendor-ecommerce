package hust.hoangson.auth.response;

import hust.hoangson.auth.domain.entity.User;
import hust.hoangson.auth.domain.enums.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String userRef;
    private String email;
    private Role role;

    public static AuthResponse fromEntity(User user, String token) {
        return AuthResponse.builder()
                .userRef(user.getUserRef())
                .email(user.getEmail())
                .accessToken(token)
                .tokenType("Bearer")
                .role(Role.fromCode(user.getRole()))
                .build();
    }

    public static AuthResponse fromEntity(String token) {
        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .build();
    }
}