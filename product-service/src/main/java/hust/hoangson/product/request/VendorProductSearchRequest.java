package hust.hoangson.product.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VendorProductSearchRequest {
    private String vendorProductId;
    private String vendorId;
    private String catalogId;

    private String name;
    private String description;
    private String brand;

    private BigDecimal priceFrom;
    private BigDecimal priceTo;

    private Integer status;

    private int page = 0;
    private int limit = 20;
}
