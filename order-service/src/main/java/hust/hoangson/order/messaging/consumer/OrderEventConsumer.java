package hust.hoangson.order.messaging.consumer;

import hust.hoangson.common.kafka.event.product.ProductVariantCheckResponseEvent;
import hust.hoangson.order.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final CartService cartService;

    @KafkaListener(topics = "product-variant-response", groupId = "order-service")
    public void onVariantResponse(ProductVariantCheckResponseEvent event) {
        try {
            log.info("📥 Received CartItemAddResponseEvent: requestId={}, success={}", event.getRequestId(), event.isSuccess());

            if (!event.isSuccess()) {
                log.warn("Variant lookup failed for variantId={} reason={}", event.getVariantId(), event.getReason());
                // optional: publish a failed event / notify user
                return;
            }

            cartService.handleVariantResponse(
                    event.getCartId(),
                    event.getVariantId(),
                    event.getProductId(),
                    event.getProductName(),
                    event.getProductUrl(),
                    event.getPrice()
            );

            log.info("✅ Cart item saved for cartId={} variantId={}", event.getCartId(), event.getVariantId());
        } catch (Exception ex) {
            log.error("❌ Error processing CartItemAddResponseEvent requestId={}, cause={}", event.getRequestId(), ex.getMessage(), ex);
        }
    }
}