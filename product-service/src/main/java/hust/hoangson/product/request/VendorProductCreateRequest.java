package hust.hoangson.product.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VendorProductCreateRequest {
    private String vendorId;
    private String catalogId;
    private String name;
    private BigDecimal price;
    private Integer stockQuantity;
    private int status;
}
