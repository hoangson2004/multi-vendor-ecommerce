package hust.hoangson.common.kafka.event.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantCheckResponseEvent {
    private String requestId;
    private String cartId;
    private String variantId;
    private String productId;
    private String productName;
    private String productUrl;
    private BigDecimal price;
    private String reason;
    private boolean success;
}