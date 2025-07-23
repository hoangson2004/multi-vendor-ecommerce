package hust.hoangson.user.request;

import lombok.Data;

import java.util.UUID;

@Data
public class SearchUserRequest {
    private UUID userId;
    private String username;
    private String email;
    private String phone;
    private String fullname;
    private String role;
}
