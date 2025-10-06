package hust.hoangson.common.kafka.event.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantCheckRequestEvent {
    private String requestId;
    private String cartId;
    private String variantId;
}