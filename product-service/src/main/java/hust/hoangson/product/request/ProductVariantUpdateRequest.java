package hust.hoangson.product.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ProductVariantUpdateRequest {
    private String sku;
    private Map<String, Object> attributesJson;
    private BigDecimal price;
    private Integer stockQuantity;
}
