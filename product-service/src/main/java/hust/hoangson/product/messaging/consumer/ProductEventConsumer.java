package hust.hoangson.product.messaging.consumer;

import hust.hoangson.common.kafka.config.KafkaGroups;
import hust.hoangson.common.kafka.config.KafkaTopics;
import hust.hoangson.common.kafka.event.order.ProductVariantCheckRequestEvent;
import hust.hoangson.product.messaging.producer.ProductEventPublisher;
import hust.hoangson.product.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductEventConsumer {

    private final ProductVariantService productVariantService;
    private final ProductEventPublisher productEventPublisher;

    @KafkaListener(
            topics = KafkaTopics.PRODUCT_VARIANT_CHECK_REQUEST,
            groupId = KafkaGroups.PRODUCT_SERVICE_GROUP
    )
    public void onVariantCheckRequested(ProductVariantCheckRequestEvent event) {
        log.info("📥 Received ProductVariantCheckRequestEvent: {}", event);

        try {
//            var response = productVariantService.checkVariant(event);
//            productEventPublisher.publishVariantCheckResponse(response);

            log.info("✅ Published ProductVariantCheckResponseEvent for requestId={}", event.getRequestId());
        } catch (Exception ex) {
            log.error("❌ Failed to process variant check for requestId={}, reason={}",
                    event.getRequestId(), ex.getMessage(), ex);
        }
    }
}

