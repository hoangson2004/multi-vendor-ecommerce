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

}