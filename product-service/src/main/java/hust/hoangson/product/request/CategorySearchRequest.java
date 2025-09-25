package hust.hoangson.product.request;

import lombok.Data;

@Data
public class CategorySearchRequest {
    private String categoryId;
    private String name;
    private String slug;
    private int page = 0;
    private int limit = 10;
}
