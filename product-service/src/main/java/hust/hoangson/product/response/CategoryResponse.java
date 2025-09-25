package hust.hoangson.product.response;

import hust.hoangson.product.domain.entity.ProductCategoryEntity;
import lombok.Data;

@Data
public class CategoryResponse {
    private String categoryId;
    private String parentId;
    private String name;
    private String slug;
    private String description;

    public static CategoryResponse of(ProductCategoryEntity entity) {
        CategoryResponse res = new CategoryResponse();
        res.categoryId = entity.getCategoryId();
        res.parentId = entity.getParent() != null ? entity.getParent().getCategoryId() : null;
        res.name = entity.getName();
        res.slug = entity.getSlug();
        res.description = entity.getDescription();
        return res;
    }
}
