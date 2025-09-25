package hust.hoangson.product.service;

import hust.hoangson.product.request.CategoryCreateRequest;
import hust.hoangson.product.request.CategorySearchRequest;
import hust.hoangson.product.request.CategoryUpdateRequest;
import hust.hoangson.product.response.CategoryResponse;
import org.springframework.data.domain.Page;

public interface ProductCategoryService {
    CategoryResponse create(CategoryCreateRequest req);
    Page<CategoryResponse> search(CategorySearchRequest req);
    CategoryResponse update(String categoryId, CategoryUpdateRequest req);
    int delete(String categoryId);
}
