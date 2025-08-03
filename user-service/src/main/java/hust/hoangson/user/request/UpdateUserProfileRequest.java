package hust.hoangson.user.request;

import lombok.Data;

@Data
public class UpdateUserProfileRequest {
    private String fullName;
    private String email;
    private String phone;
    private String avatarUrl;
}