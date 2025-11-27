package hust.hoangson.order.response;

import hust.hoangson.order.domain.entity.OrderItemEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderItemResponse {
    private UUID id;
    private String variantId;
    private Integer quantity;
    private BigDecimal price;

    public static OrderItemResponse of(OrderItemEntity entity) {
        OrderItemResponse r = new OrderItemResponse();
        r.id = entity.getId();
        r.variantId = entity.getVariantId();
        r.quantity = entity.getQuantity();
        r.price = entity.getPrice();
        return r;
    }
}
