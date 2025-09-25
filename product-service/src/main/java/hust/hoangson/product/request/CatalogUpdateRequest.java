package hust.hoangson.product.request;

import lombok.Data;

import java.util.Map;

@Data
public class CatalogUpdateRequest {
    private String name;
    private String description;
    private String brand;
    private Map<String, Object> attributesJson;
}
