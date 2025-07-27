package hust.hoangson.common.kafka.event.user;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedEvent {
    private String userId;
    private String username;
    private String email;
    private String role;
    private String fullname;
}
