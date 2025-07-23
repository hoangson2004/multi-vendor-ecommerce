package hust.hoangson.user.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String userId;
    private String username;
    private String email;
    private String phone;
    private String fullName;
    private String avatarUrl;
    private Boolean isActive;
    private String createdAt;
    private String updatedAt;
    private String role;
}
