package hust.hoangson.product.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantDTO {
    private String variantId;
    private String sku;
    private Map<String, Object> attributesJson;
    private BigDecimal price;
    private Integer stockQuantity;
    private String vendorProductId;
    private String url;
}
