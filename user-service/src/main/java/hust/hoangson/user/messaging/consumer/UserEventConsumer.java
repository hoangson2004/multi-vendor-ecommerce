package hust.hoangson.user.messaging.consumer;

import hust.hoangson.common.kafka.event.user.UserCreateFailedEvent;
import hust.hoangson.common.kafka.event.user.UserCreatedEvent;
import hust.hoangson.user.messaging.producer.UserEventPublisher;
import hust.hoangson.user.serivce.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEventConsumer {
    private final UserProfileService userProfileService;
    private final UserEventPublisher userEventPublisher;

    @KafkaListener(topics = "user-created", groupId = "user-service-group")
    public void onUserCreated(UserCreatedEvent event) {
        log.info("📥 Received UserCreatedEvent: {}", event);
        try {
            userProfileService.createUserFromEvent(event);
            log.info("✅ User profile created successfully for userId={}", event.getUserId());
        } catch (Exception ex) {
            log.error("❌ Failed to create user profile for userId={}, reason={}",
                    event.getUserId(), ex.getMessage());

            userEventPublisher.publishUserCreateFailed(
                    new UserCreateFailedEvent(event.getUserId(), ex.getMessage())
            );
        }
    }
}
