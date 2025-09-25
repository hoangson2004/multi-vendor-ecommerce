package hust.hoangson.product.controller;

import hust.hoangson.product.request.ProductVariantCreateRequest;
import hust.hoangson.product.request.ProductVariantUpdateRequest;
import hust.hoangson.product.response.BaseResponse;
import hust.hoangson.product.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@RestController
@RequestMapping("/api/product/variants")
@RequiredArgsConstructor
public class ProductVariantController {

    private final ProductVariantService productVariantService;

    @GetMapping("/by-vendor-product/{vendorProductId}")
    public ResponseEntity<?> getByVendorProductId(@PathVariable String vendorProductId) {
        return ResponseEntity.ok(BaseResponse.success(productVariantService.getByVendorProduct(vendorProductId)));
    }

    @GetMapping("/{variantId}")
    public ResponseEntity<?> getByVariantId(@PathVariable String variantId) {
        return ResponseEntity.ok(BaseResponse.success(productVariantService.getByVariantId(variantId)));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductVariantCreateRequest req) {
        return ResponseEntity.ok(BaseResponse.success(productVariantService.create(req)));
    }

    @PutMapping("/{variantId}")
    public ResponseEntity<?> update(@PathVariable String variantId,
                                         @RequestBody ProductVariantUpdateRequest req) {
        return ResponseEntity.ok(BaseResponse.success(productVariantService.update(variantId, req)));
    }

    @DeleteMapping("/{variantId}")
    public ResponseEntity<?> delete(@PathVariable String variantId) {
        return ResponseEntity.ok(BaseResponse.success(productVariantService.delete(variantId)));
    }

    @PostMapping("/{variantId}/images")
    public ResponseEntity<?> uploadImage(
            @PathVariable String variantId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "isPrimary", defaultValue = "false") boolean isPrimary
    ) {
        return ResponseEntity.ok(BaseResponse
                .success(productVariantService.uploadVariantImage(variantId, file, isPrimary)));
    }

    @GetMapping("/{variantId}/images")
    public ResponseEntity<?> getImages(@PathVariable String variantId) {
        return ResponseEntity.ok(BaseResponse.success(productVariantService.getVariantImages(variantId)));
    }

    @DeleteMapping("/{variantId}/images/{imageId}")
    public ResponseEntity<?> deleteImage(
            @PathVariable String variantId,
            @PathVariable UUID imageId
    ) {
        return ResponseEntity.ok(productVariantService.deleteImage(variantId, imageId));
    }
}
