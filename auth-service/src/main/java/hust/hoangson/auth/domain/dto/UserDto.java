package hust.hoangson.auth.domain.dto;

import hust.hoangson.auth.domain.enums.Role;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String fullName;
    private Role role;
}