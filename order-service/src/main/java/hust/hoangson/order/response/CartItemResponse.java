package hust.hoangson.order.response;

import hust.hoangson.order.domain.entity.CartItemEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CartItemResponse {
    private UUID id;
    private String vendorProductId;
    private String vendorProductName;
    private String vendorProductUrl;
    private String variantId;
    private Integer quantity;
    private BigDecimal price;

    public static CartItemResponse of(CartItemEntity entity) {
        CartItemResponse response = new CartItemResponse();
        response.id = entity.getId();
        response.vendorProductId = entity.getVendorProductId();
        response.vendorProductName = entity.getVendorProductName();
        response.vendorProductUrl = entity.getVendorProductUrl();
        response.variantId = entity.getVariantId();
        response.quantity = entity.getQuantity();
        response.price = entity.getPrice();
        return response;
    }
}
