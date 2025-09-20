package hust.hoangson.product.controller;

import hust.hoangson.product.request.VendorProductCreateRequest;
import hust.hoangson.product.request.VendorProductSearchRequest;
import hust.hoangson.product.request.VendorProductUpdateRequest;
import hust.hoangson.product.response.BaseResponse;
import hust.hoangson.product.service.VendorProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
