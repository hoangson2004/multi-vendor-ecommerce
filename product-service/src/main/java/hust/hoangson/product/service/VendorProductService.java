package hust.hoangson.product.service;

import hust.hoangson.product.request.VendorProductCreateRequest;
import hust.hoangson.product.request.VendorProductSearchRequest;
import hust.hoangson.product.request.VendorProductUpdateRequest;
import hust.hoangson.product.response.ImageResponse;
import hust.hoangson.product.response.VendorProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface VendorProductService {
    Page<VendorProductResponse> search(VendorProductSearchRequest req);

    VendorProductResponse create(VendorProductCreateRequest req);

    VendorProductResponse update(String vendorProductId, VendorProductUpdateRequest req);

    int delete(String vendorProductId);

    ImageResponse uploadImage(String vendorProductId, MultipartFile file, boolean isPrimary);

    List<ImageResponse> getImages(String variantId);

    int deleteImage(String variantId, UUID imageId);
}
