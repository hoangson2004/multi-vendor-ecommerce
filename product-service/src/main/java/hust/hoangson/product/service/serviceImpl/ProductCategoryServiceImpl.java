package hust.hoangson.product.service.serviceImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import hust.hoangson.product.domain.constant.Constant;
import hust.hoangson.product.domain.entity.ProductCatalogEntity;
import hust.hoangson.product.domain.entity.ProductCategoryEntity;
import hust.hoangson.product.domain.entity.ProductImageEntity;
import hust.hoangson.product.domain.entity.ProductVariantEntity;
import hust.hoangson.product.domain.enums.OwnerType;
import hust.hoangson.product.repository.ProductCategoryRepository;
import hust.hoangson.product.repository.ProductImageRepository;
import hust.hoangson.product.request.CategoryCreateRequest;
import hust.hoangson.product.request.CategorySearchRequest;
import hust.hoangson.product.request.CategoryUpdateRequest;
import hust.hoangson.product.response.CategoryResponse;
import hust.hoangson.product.response.ImageResponse;
import hust.hoangson.product.service.ProductCategoryService;
import hust.hoangson.product.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductImageService productImageService;

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

        return CategoryResponse.of(entity, null);
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

        return listCategory.map(entity -> {
            String url = getPrimeImg(entity.getCategoryId());
            return CategoryResponse.of(entity, url);
            });
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

        String url = getPrimeImg(entity.getCategoryId());

        return CategoryResponse.of(entity, url);
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

    @Override
    public ImageResponse uploadImage(String categoryId, MultipartFile file, boolean isPrimary) {
        ProductCategoryEntity category = productCategoryRepository.findByCategoryId(categoryId).orElse(null);
        if (category == null) return null;

        UUID categoryUuid = category.getId();

        return productImageService.uploadImage(categoryUuid, categoryId, file, isPrimary, OwnerType.CATEGORY);
    }

    @Override
    public List<ImageResponse> getImages(String categoryId) {
        return productImageService.getImages(categoryId);
    }

    @Override
    public int deleteImage(String categoryId, UUID imageId) {
        return productImageService.deleteImage(imageId);
    }

    public String getPrimeImg(String categoryId) {
        return productImageService.getPrimeImg(categoryId);
    }
}
