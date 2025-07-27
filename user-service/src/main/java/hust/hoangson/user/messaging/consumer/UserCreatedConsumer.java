package hust.hoangson.user.messaging.consumer;

import hust.hoangson.common.kafka.event.user.UserCreatedEvent;
import hust.hoangson.user.serivce.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCreatedConsumer {
    private final UserService userService;

    @KafkaListener(topics = "user-created", groupId = "user-service-group")
    public void consume(UserCreatedEvent event) {
        log.info("📥 Received UserCreatedEvent: {}", event);

        userService.createUserFromEvent(event);
    }
}
