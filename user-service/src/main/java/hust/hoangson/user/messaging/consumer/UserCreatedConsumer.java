package hust.hoangson.user.messaging.consumer;

import hust.hoangson.common.kafka.event.user.UserCreatedEvent;
import hust.hoangson.user.serivce.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCreatedConsumer {
    private final UserProfileService userProfileService;

    @KafkaListener(topics = "user-created", groupId = "user-service-group")
    public void consume(UserCreatedEvent event) {
        log.info("📥 Received UserCreatedEvent: {}", event);

        userProfileService.createUserFromEvent(event);
    }
}
