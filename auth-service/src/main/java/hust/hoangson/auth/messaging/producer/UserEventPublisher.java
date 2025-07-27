package hust.hoangson.auth.messaging.producer;

import hust.hoangson.common.kafka.config.KafkaTopics;
import hust.hoangson.common.kafka.event.user.UserCreatedEvent;
import hust.hoangson.common.kafka.producer.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventPublisher {

    private final KafkaProducerService kafkaProducerService;

    public void publishUserCreated(UserCreatedEvent event) {
        kafkaProducerService.send(KafkaTopics.USER_CREATED, event.getUserId(), event);
    }
}
