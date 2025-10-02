package hust.hoangson.product.controller;

import hust.hoangson.product.request.VendorProductCreateRequest;
import hust.hoangson.product.request.VendorProductSearchRequest;
import hust.hoangson.product.request.VendorProductUpdateRequest;
import hust.hoangson.product.response.BaseResponse;
import hust.hoangson.product.service.VendorProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/product/vendor-products")
@RequiredArgsConstructor
public class VendorProductController {

    private final VendorProductService vendorProductService;

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody VendorProductSearchRequest req) {
        return ResponseEntity.ok(BaseResponse.success(vendorProductService.search(req)));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody VendorProductCreateRequest req) {
        return ResponseEntity.ok(BaseResponse.success(vendorProductService.create(req)));
    }

    @PutMapping("/{vendorProductId}")
    public ResponseEntity<?> update(@PathVariable String vendorProductId,
                                    @RequestBody VendorProductUpdateRequest req) {
        return ResponseEntity.ok(BaseResponse.success(vendorProductService.update(vendorProductId, req)));
    }

    @DeleteMapping("/{vendorProductId}")
    public ResponseEntity<?> delete(@PathVariable String vendorProductId) {
        return ResponseEntity.ok(BaseResponse.success(vendorProductService.delete(vendorProductId)));
    }

    @PostMapping("/{vendorProductId}/images")
    public ResponseEntity<?> uploadImage(
            @PathVariable String vendorProductId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "isPrimary", defaultValue = "false") boolean isPrimary
    ) {
        return ResponseEntity.ok(BaseResponse
                .success(vendorProductService.uploadImage(vendorProductId, file, isPrimary)));
    }

    @GetMapping("/{vendorProductId}/images")
    public ResponseEntity<?> getImages(@PathVariable String vendorProductId) {
        return ResponseEntity.ok(BaseResponse.success(vendorProductService.getImages(vendorProductId)));
    }

    @DeleteMapping("/{vendorProductId}/images/{imageId}")
    public ResponseEntity<?> deleteImage(
            @PathVariable String vendorProductId,
            @PathVariable UUID imageId
    ) {
        return ResponseEntity.ok(vendorProductService.deleteImage(vendorProductId, imageId));
    }
}
