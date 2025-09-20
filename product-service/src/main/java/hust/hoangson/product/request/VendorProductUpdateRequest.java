package hust.hoangson.product.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VendorProductUpdateRequest {
    private BigDecimal price;
    private Integer stockQuantity;
    private int status;
    private String catalogId;
}
