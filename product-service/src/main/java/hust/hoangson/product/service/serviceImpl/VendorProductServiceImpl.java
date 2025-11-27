package hust.hoangson.product.service.serviceImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import hust.hoangson.product.domain.constant.Constant;
import hust.hoangson.product.domain.entity.ProductCatalogEntity;
import hust.hoangson.product.domain.entity.ProductImageEntity;
import hust.hoangson.product.domain.entity.VendorProductEntity;
import hust.hoangson.product.domain.enums.OwnerType;
import hust.hoangson.product.domain.enums.ProductStatus;
import hust.hoangson.product.repository.ProductCatalogRepository;
import hust.hoangson.product.repository.ProductImageRepository;
import hust.hoangson.product.repository.VendorProductRepository;
import hust.hoangson.product.request.VendorProductCreateRequest;
import hust.hoangson.product.request.VendorProductSearchRequest;
import hust.hoangson.product.request.VendorProductUpdateRequest;
import hust.hoangson.product.response.ImageResponse;
import hust.hoangson.product.response.VendorProductResponse;
import hust.hoangson.product.service.ProductImageService;
import hust.hoangson.product.service.VendorProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VendorProductServiceImpl implements VendorProductService {

    private final VendorProductRepository vendorProductRepository;
    private final ProductCatalogRepository productCatalogRepository;
    private final ProductImageService productImageService;

    @Override
    public Page<VendorProductResponse> search(VendorProductSearchRequest req) {
        PageRequest pageable = PageRequest.of(req.getPage(), req.getLimit());

        Page<VendorProductEntity> results = vendorProductRepository.searchVendorProducts(
                req.getVendorProductId(),
                req.getVendorId(),
                req.getCatalogId(),
                req.getName() != null ? req.getName() : "",
                req.getDescription() != null ? req.getDescription() : "",
                req.getBrand() != null ? req.getBrand() : "",
                req.getPriceFrom(),
                req.getPriceTo(),
                req.getStatus(),
                pageable
        );

        return results.map(entity ->{
            String url = getPrimeImg(entity.getVendorProductId());
            return VendorProductResponse.of(entity, url);
        });
    }

    @Override
    public VendorProductResponse create(VendorProductCreateRequest req) {
        if (req.getCatalogId() == null) {
            return null;
        }

        ProductCatalogEntity catalogEntity = productCatalogRepository.findByCatalogId(req.getCatalogId()).orElse(null);

        if (catalogEntity == null) {
            return null;
        }

        VendorProductEntity entity = new VendorProductEntity();

        entity.setVendorProductId("PROD-" + System.currentTimeMillis());
        entity.setVendorId(req.getVendorId());
        entity.setName(req.getName() != null ? req.getName() : catalogEntity.getName());
        entity.setProductCatalog(catalogEntity);
        entity.setPrice(req.getPrice());
        entity.setStockQuantity(req.getStockQuantity());
        entity.setStatus(req.getStatus());

        VendorProductEntity saved = vendorProductRepository.save(entity);

        return VendorProductResponse.of(saved, null);
    }

    @Override
    public VendorProductResponse update(String vendorProductId, VendorProductUpdateRequest req) {
        VendorProductEntity entity = vendorProductRepository.findByVendorProductId(vendorProductId)
                .orElse(null);
        if (entity == null) {
            return null;
        }

        if (req.getCatalogId() != null) {
            productCatalogRepository.findByCatalogId(req.getCatalogId()).ifPresent(entity::setProductCatalog);
        }

        if (req.getPrice() != null) {
            entity.setPrice(req.getPrice());
        }

        if (req.getStockQuantity() != null) {
            entity.setStockQuantity(req.getStockQuantity());
        }

        entity.setStatus(ProductStatus.fromCode(req.getStatus()) != null ? req.getStatus() : entity.getStatus());

        VendorProductEntity updated = vendorProductRepository.save(entity);
        String url = getPrimeImg(updated.getVendorProductId());

        return VendorProductResponse.of(updated, url);
    }

    @Override
    @Transactional
    public int delete(String vendorProductId) {
        return vendorProductRepository.deleteByVendorProductId(vendorProductId);
    }

    @Override
    public ImageResponse uploadImage(String vendorProductId, MultipartFile file, boolean isPrimary) {
        VendorProductEntity product = vendorProductRepository.findByVendorProductId(vendorProductId).orElse(null);
        if (product == null) return null;

        UUID productUuid = product.getId();

        return productImageService.uploadImage(productUuid, vendorProductId, file, isPrimary, OwnerType.VENDOR_PRODUCT);
    }

    @Override
    public List<ImageResponse> getImages(String vendorProductId) {
        return productImageService.getImages(vendorProductId);
    }

    @Override
    public int deleteImage(String vendorProductId, UUID imageId) {
        return productImageService.deleteImage(imageId);
    }

    public String getPrimeImg(String productId) {
        return productImageService.getPrimeImg(productId);
    }
}
