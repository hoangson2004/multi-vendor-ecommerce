package hust.hoangson.user.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.UUID;

@Data
public class SearchUserRequest {
    private String userId;
    private String username;
    private String email;
    private String phone;
    private String fullname;
    private String role;
    @Min(value = 0)
    private int page = 0;
    @Min(value = 1)
    private int limit = 10;
}
