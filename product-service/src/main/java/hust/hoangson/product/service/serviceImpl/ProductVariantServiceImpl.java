package hust.hoangson.product.service.serviceImpl;

import hust.hoangson.product.domain.entity.ProductVariantEntity;
import hust.hoangson.product.domain.entity.VendorProductEntity;
import hust.hoangson.product.repository.ProductVariantRepository;
import hust.hoangson.product.repository.VendorProductRepository;
import hust.hoangson.product.request.ProductVariantCreateRequest;
import hust.hoangson.product.request.ProductVariantUpdateRequest;
import hust.hoangson.product.response.ProductVariantResponse;
import hust.hoangson.product.service.ProductVariantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final VendorProductRepository vendorProductRepository;

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
        return ProductVariantResponse.of(saved);
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
        return ProductVariantResponse.of(updated);
    }

    @Override
    @Transactional
    public int delete(String variantId) {
        return productVariantRepository.deleteByVariantId(variantId);
    }

    @Override
    public ProductVariantResponse getByVariantId(String variantId) {
        return productVariantRepository.findByVariantId(variantId)
                .map(ProductVariantResponse::of)
                .orElse(null);
    }

    @Override
    public List<ProductVariantResponse> getByVendorProduct(String vendorProductId) {
        return productVariantRepository.findByVendorProductId(vendorProductId)
                .stream()
                .map(ProductVariantResponse::of)
                .toList();
    }
}
