package hust.hoangson.order.request;

import lombok.Data;

@Data
public class AddToCartRequest {
    private String variantId;
    private Integer quantity;
}