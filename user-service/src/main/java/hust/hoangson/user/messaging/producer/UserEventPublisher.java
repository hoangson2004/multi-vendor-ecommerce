package hust.hoangson.user.messaging.producer;

import hust.hoangson.common.kafka.config.KafkaTopics;
import hust.hoangson.common.kafka.event.user.UserCreateFailedEvent;
import hust.hoangson.common.kafka.producer.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventPublisher {
    private final KafkaProducerService kafkaProducerService;

    public void publishUserCreateFailed(UserCreateFailedEvent event) {
        kafkaProducerService.send(KafkaTopics.USER_CREATE_FAILED, event.getUserId(), event);
    }
}
