package hust.hoangson.product.service;

import hust.hoangson.product.request.ProductVariantCreateRequest;
import hust.hoangson.product.request.ProductVariantUpdateRequest;
import hust.hoangson.product.response.ProductVariantResponse;

import java.util.List;

public interface ProductVariantService {
    ProductVariantResponse create(ProductVariantCreateRequest req);
    ProductVariantResponse update(String variantId, ProductVariantUpdateRequest req);
    int delete(String variantId);
    ProductVariantResponse getByVariantId(String variantId);
    List<ProductVariantResponse> getByVendorProduct(String vendorProductId);
}
