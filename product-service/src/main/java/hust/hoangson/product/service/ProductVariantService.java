package hust.hoangson.product.service;

import hust.hoangson.product.request.ProductVariantCreateRequest;
import hust.hoangson.product.request.ProductVariantUpdateRequest;
import hust.hoangson.product.response.ImageResponse;
import hust.hoangson.product.response.ProductVariantResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductVariantService {
    ProductVariantResponse create(ProductVariantCreateRequest req);

    ProductVariantResponse update(String variantId, ProductVariantUpdateRequest req);

    int delete(String variantId);

    ProductVariantResponse getByVariantId(String variantId);

    List<ProductVariantResponse> getByVendorProduct(String vendorProductId);

    ImageResponse uploadVariantImage(String variantId, MultipartFile file, boolean isPrimary);

    List<ImageResponse> getVariantImages(String variantId);

    int deleteImage(String variantId, UUID imageId);
}
