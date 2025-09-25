package hust.hoangson.product.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CatalogSearchRequest {
    private String catalogId;
    private String name;
    private String description;
    private String brand;
    @Min(value = 0)
    private int page = 0;
    @Min(value = 1)
    private int limit = 10;
}
