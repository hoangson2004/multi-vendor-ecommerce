package hust.hoangson.product.service.serviceImpl;

import hust.hoangson.product.domain.entity.ProductCategoryEntity;
import hust.hoangson.product.repository.ProductCategoryRepository;
import hust.hoangson.product.request.CategoryCreateRequest;
import hust.hoangson.product.request.CategorySearchRequest;
import hust.hoangson.product.request.CategoryUpdateRequest;
import hust.hoangson.product.response.CategoryResponse;
import hust.hoangson.product.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public CategoryResponse create(CategoryCreateRequest req) {
        ProductCategoryEntity entity = new ProductCategoryEntity();
        entity.setCategoryId("CATGO-" + System.currentTimeMillis());
        entity.setName(req.getName());
        entity.setSlug(req.getSlug());
        entity.setDescription(req.getDescription());

        if (req.getParentId() != null) {
            productCategoryRepository.findByCategoryId(req.getParentId()).ifPresent(entity::setParent);
        }

        productCategoryRepository.save(entity);

        return CategoryResponse.of(entity);
    }

    @Override
    public Page<CategoryResponse> search(CategorySearchRequest req) {
        PageRequest pageable  = PageRequest.of(req.getPage(), req.getLimit());

        Page<ProductCategoryEntity> listCategory = productCategoryRepository.searchCategories(
                req.getCategoryId(),
                req.getName() != null ? req.getName() : "",
                req.getSlug() != null ? req.getSlug() : "",
                pageable
        );
        return listCategory.map(CategoryResponse::of);
    }

    @Override
    public CategoryResponse update(String categoryId, CategoryUpdateRequest req) {
        ProductCategoryEntity entity = productCategoryRepository.findByCategoryId(categoryId).orElse(null);

        if (entity == null) {
            return null;
        }

        entity.setName(req.getName());
        entity.setDescription(req.getDescription());
        entity.setSlug(req.getSlug());
        if (req.getParentId() == null) {
            entity.setParent(null);
        } else {
            productCategoryRepository.findByCategoryId(req.getParentId()).ifPresent(entity::setParent);
        }

        productCategoryRepository.save(entity);
        return CategoryResponse.of(entity);
    }

    @Override
    @Transactional
    public int delete(String categoryId) {
        boolean hasChildren = productCategoryRepository.existsByParent_CategoryId(categoryId);
        if (hasChildren) {
            return 0;
        }
        return productCategoryRepository.softDeleteByCategoryId(categoryId);
    }
}
