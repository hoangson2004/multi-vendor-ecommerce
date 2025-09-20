package hust.hoangson.product.response;

import hust.hoangson.product.domain.entity.ProductCatalogEntity;
import lombok.Data;

import java.util.Map;

@Data
public class CatalogResponse {
    private String catalogId;
    private String name;
    private String description;
    private String brand;
    private Map<String, Object> attributesJson;

    public static CatalogResponse of(ProductCatalogEntity entity) {
        CatalogResponse res = new CatalogResponse();
        res.catalogId = entity.getCatalogId();
        res.name = entity.getName();
        res.description = entity.getDescription();
        res.brand = entity.getBrand();
        res.attributesJson = entity.getAttributesJson();
        return res;
    }
}
