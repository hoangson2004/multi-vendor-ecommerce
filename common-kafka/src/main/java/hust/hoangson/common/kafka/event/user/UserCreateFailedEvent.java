package hust.hoangson.common.kafka.event.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateFailedEvent {
    private String userId;
    private String reason;
}
