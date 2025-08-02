package hust.hoangson.auth.messaging.consumer;

import hust.hoangson.auth.service.AuthService;
import hust.hoangson.common.kafka.config.KafkaGroups;
import hust.hoangson.common.kafka.config.KafkaTopics;
import hust.hoangson.common.kafka.event.user.UserCreateFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEventConsumer {
    private final AuthService authService;

    @KafkaListener(topics = KafkaTopics.USER_CREATE_FAILED, groupId = KafkaGroups.AUTH_SERVICE_GROUP)
    public void onUserCreateFailed(UserCreateFailedEvent event) {
        try {
            log.warn("⚠️ Received rollback event for userId={} reason={}",
                    event.getUserId(), event.getReason());

            int deleted = authService.deleteUser(event.getUserId());

            if (deleted > 0) {
                log.info("✅ Rolled back userAuth for userId={}", event.getUserId());
            } else {
                log.error("❌ Rollback failed (but suppressing exception) for userId={}", event.getUserId());
            }

        } catch (Exception ex) {
            log.error("❌ Unexpected error during rollback userId={}, suppressing exception. Cause={}",
                    event.getUserId(), ex.getMessage(), ex);
        }
    }
}
