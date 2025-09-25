package hust.hoangson.product.request;

import lombok.Data;

import java.util.Map;

@Data
public class CatalogCreateRequest {
    private String name;
    private String description;
    private String brand;
    private Map<String, Object> attributesJson;
}
