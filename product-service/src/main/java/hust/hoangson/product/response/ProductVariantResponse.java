package hust.hoangson.product.response;


import hust.hoangson.product.domain.entity.ProductVariantEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class ProductVariantResponse {
    private String variantId;
    private String sku;
    private Map<String, Object> attributesJson;
    private BigDecimal price;
    private Integer stockQuantity;
    private String vendorProductId;
    private String url;

    public static ProductVariantResponse of(ProductVariantEntity entity, String url) {
        return ProductVariantResponse.builder()
                .variantId(entity.getVariantId())
                .sku(entity.getSku())
                .attributesJson(entity.getAttributesJson())
                .price(entity.getPrice())
                .stockQuantity(entity.getStockQuantity())
                .vendorProductId(entity.getVendorProduct().getVendorProductId())
                .build();
    }
}
