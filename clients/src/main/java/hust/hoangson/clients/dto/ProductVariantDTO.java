package hust.hoangson.clients.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantDTO {
    private String variantId;
    private String sku;
    private Map<String, Object> attributesJson;
    private BigDecimal price;
    private Integer stockQuantity;
    private String vendorProductId;
    private String url;
}