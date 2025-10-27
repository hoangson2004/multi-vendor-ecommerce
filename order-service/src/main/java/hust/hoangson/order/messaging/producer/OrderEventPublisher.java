package hust.hoangson.order.messaging.producer;

import hust.hoangson.common.kafka.config.KafkaTopics;
import hust.hoangson.common.kafka.event.order.ProductVariantCheckRequestEvent;
import hust.hoangson.common.kafka.producer.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {
    private final KafkaProducerService kafkaProducerService;

    public void publishCartItemAddRequest(ProductVariantCheckRequestEvent event) {
        kafkaProducerService.send(KafkaTopics.PRODUCT_VARIANT_CHECK_REQUEST, event.getVariantId(), event);
    }

}
