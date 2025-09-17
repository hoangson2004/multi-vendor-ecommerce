package hust.hoangson.product.request;

import lombok.Data;

@Data
public class CategoryCreateRequest {
    private String parentId;
    private String name;
    private String slug;
    private String description;
}
