package hust.hoangson.product.service.serviceImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import hust.hoangson.product.domain.constant.Constant;
import hust.hoangson.product.domain.entity.ProductImageEntity;
import hust.hoangson.product.domain.entity.ProductVariantEntity;
import hust.hoangson.product.domain.entity.VendorProductEntity;
import hust.hoangson.product.domain.enums.OwnerType;
import hust.hoangson.product.repository.ProductImageRepository;
import hust.hoangson.product.repository.ProductVariantRepository;
import hust.hoangson.product.repository.VendorProductRepository;
import hust.hoangson.product.request.ProductVariantCreateRequest;
import hust.hoangson.product.request.ProductVariantUpdateRequest;
import hust.hoangson.product.response.ImageResponse;
import hust.hoangson.product.response.ProductVariantResponse;
import hust.hoangson.product.service.ProductImageService;
import hust.hoangson.product.service.ProductVariantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final VendorProductRepository vendorProductRepository;
    private final ProductImageService productImageService;

    @Override
    public ProductVariantResponse create(ProductVariantCreateRequest req) {
        VendorProductEntity vendorProduct = vendorProductRepository.findByVendorProductId(req.getVendorProductId())
                .orElse(null);
        if (vendorProduct == null) {
            return null;
        }

        ProductVariantEntity entity = new ProductVariantEntity();
        entity.setVariantId("VAR-" + System.currentTimeMillis());
        entity.setVendorProduct(vendorProduct);
        entity.setSku(req.getSku());
        entity.setAttributesJson(req.getAttributesJson());
        entity.setPrice(req.getPrice());
        entity.setStockQuantity(req.getStockQuantity());

        ProductVariantEntity saved = productVariantRepository.save(entity);
        return ProductVariantResponse.of(saved, null);
    }

    @Override
    public ProductVariantResponse update(String variantId, ProductVariantUpdateRequest req) {
        ProductVariantEntity entity = productVariantRepository.findByVariantId(variantId)
                .orElse(null);
        if (entity == null) {
            return null;
        }

        if (req.getSku() != null) {
            entity.setSku(req.getSku());
        }
        if (req.getAttributesJson() != null) {
            entity.setAttributesJson(req.getAttributesJson());
        }
        if (req.getPrice() != null) {
            entity.setPrice(req.getPrice());
        }
        if (req.getStockQuantity() != null) {
            entity.setStockQuantity(req.getStockQuantity());
        }

        ProductVariantEntity updated = productVariantRepository.save(entity);

        String url = getPrimeImg(variantId);
        return ProductVariantResponse.of(updated, url);
    }

    @Override
    @Transactional
    public int delete(String variantId) {
        return productVariantRepository.deleteByVariantId(variantId);
    }

    @Override
    public ProductVariantResponse getByVariantId(String variantId) {
        String url = getPrimeImg(variantId);
        return productVariantRepository.findByVariantId(variantId)
                .map(entity -> ProductVariantResponse.of(entity, url))
                .orElse(null);
    }

    @Override
    public List<ProductVariantResponse> getByVendorProduct(String vendorProductId) {
        return productVariantRepository.findByVendorProductId(vendorProductId)
                .stream()
                .map(entity -> {
                    String url = getPrimeImg(vendorProductId);
                    return ProductVariantResponse.of(entity, url);
                })
                .toList();
    }

    @Override
    public ImageResponse uploadImage(String variantId, MultipartFile file, boolean isPrimary) {
        ProductVariantEntity variant = productVariantRepository.findByVariantId(variantId).orElse(null);
        if (variant == null) return null;

        UUID variantUuid = variant.getId();

        return productImageService.uploadImage(variantUuid, variantId, file, isPrimary, OwnerType.VARIANT);
    }

    @Override
    public List<ImageResponse> getImages(String variantId) {
        return productImageService.getImages(variantId);
    }

    @Override
    public int deleteImage(String variantId, UUID imageId) {
        return productImageService.deleteImage(imageId);
    }

    public String getPrimeImg(String variantId) {
        return productImageService.getPrimeImg(variantId);
    }
}
