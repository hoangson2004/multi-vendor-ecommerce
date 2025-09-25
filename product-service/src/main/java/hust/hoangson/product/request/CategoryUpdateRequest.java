package hust.hoangson.product.request;

import lombok.Data;

@Data
public class CategoryUpdateRequest {
    private String name;
    private String slug;
    private String description;
    private String parentId;
}
