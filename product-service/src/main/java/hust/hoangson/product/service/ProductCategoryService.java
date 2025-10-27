package hust.hoangson.product.service;

import hust.hoangson.product.request.CategoryCreateRequest;
import hust.hoangson.product.request.CategorySearchRequest;
import hust.hoangson.product.request.CategoryUpdateRequest;
import hust.hoangson.product.response.CategoryResponse;
import hust.hoangson.product.response.ImageResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductCategoryService {
    CategoryResponse create(CategoryCreateRequest req);

    Page<CategoryResponse> search(CategorySearchRequest req);

    CategoryResponse update(String categoryId, CategoryUpdateRequest req);

    int delete(String categoryId);

    ImageResponse uploadImage(String categoryId, MultipartFile file, boolean isPrimary);

    List<ImageResponse> getImages(String categoryId);

    int deleteImage(String categoryId, UUID imageId);
}
