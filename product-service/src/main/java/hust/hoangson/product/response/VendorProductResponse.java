package hust.hoangson.product.response;

import hust.hoangson.product.domain.entity.ProductCatalogEntity;
import hust.hoangson.product.domain.entity.VendorProductEntity;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class VendorProductResponse {
    private String vendorProductId;
    private String vendorId;
    private String brand;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private int status;
    private String url;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static VendorProductResponse of(VendorProductEntity entity, String url) {
        VendorProductResponse res = new VendorProductResponse();

        ProductCatalogEntity catalog = entity.getProductCatalog();

        res.setVendorProductId(entity.getVendorProductId());
        res.setVendorId(entity.getVendorId());
        res.setBrand(catalog.getBrand());
        res.setName(catalog.getName());
        res.setDescription(catalog.getDescription());
        res.setPrice(entity.getPrice());
        res.setStockQuantity(entity.getStockQuantity());
        res.setStatus(entity.getStatus());
        res.setCreatedAt(entity.getCreatedAt());
        res.setUpdatedAt(entity.getUpdatedAt());

        return res;
    }
}
