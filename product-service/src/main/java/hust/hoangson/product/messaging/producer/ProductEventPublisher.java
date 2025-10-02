package hust.hoangson.product.messaging.producer;

import hust.hoangson.common.kafka.config.KafkaTopics;
import hust.hoangson.common.kafka.event.product.ProductVariantCheckResponseEvent;
import hust.hoangson.common.kafka.producer.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductEventPublisher {

    private final KafkaProducerService kafkaProducerService;

    public void publishVariantCheckResponse(ProductVariantCheckResponseEvent event) {
        kafkaProducerService.send(
                KafkaTopics.PRODUCT_VARIANT_CHECK_RESPONSE,
                event.getRequestId(),
                event
        );
    }
}
