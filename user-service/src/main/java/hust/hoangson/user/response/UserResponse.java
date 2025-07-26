package hust.hoangson.user.response;

import hust.hoangson.user.domain.dto.UserDTO;
import hust.hoangson.user.domain.entity.UserProfileEntity;
import lombok.*;

import java.time.LocalDateTime;

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
    private String role;
    private String provinceCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserResponse of(UserProfileEntity dto) {
        UserResponse res = new UserResponse();
        res.userId = dto.getUserId();
        res.username = dto.getUsername();
        res.email = dto.getEmail();
        res.phone = dto.getPhone();
        res.fullName = dto.getFullName();
        res.avatarUrl = dto.getAvatarUrl();
        res.createdAt = dto.getCreatedAt();
        res.updatedAt = dto.getUpdatedAt();
        return res;
    }
}
