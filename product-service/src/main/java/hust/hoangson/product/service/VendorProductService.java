package hust.hoangson.product.service;

import hust.hoangson.product.request.VendorProductCreateRequest;
import hust.hoangson.product.request.VendorProductSearchRequest;
import hust.hoangson.product.request.VendorProductUpdateRequest;
import hust.hoangson.product.response.VendorProductResponse;
import org.springframework.data.domain.Page;

public interface VendorProductService {
    Page<VendorProductResponse> search(VendorProductSearchRequest req);

    VendorProductResponse create(VendorProductCreateRequest req);

    VendorProductResponse update(String vendorProductId, VendorProductUpdateRequest req);

    int delete(String vendorProductId);
}
