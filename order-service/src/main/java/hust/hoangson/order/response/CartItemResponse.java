package hust.hoangson.order.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CartItemResponse {
    private UUID id;
    private String productId;
    private String productName;
    private String productUrl;
    private String variantId;
    private Integer quantity;
    private BigDecimal price;
}
