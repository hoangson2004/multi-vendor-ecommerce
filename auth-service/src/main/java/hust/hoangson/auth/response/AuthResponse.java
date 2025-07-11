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
    private String refreshToken;

    private String tokenType = "Bearer";

    public static AuthResponse fromEntity(String token, String refreshToken) {
        return AuthResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }
}