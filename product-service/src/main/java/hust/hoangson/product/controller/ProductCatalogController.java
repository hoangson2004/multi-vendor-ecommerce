package hust.hoangson.product.controller;

import hust.hoangson.product.request.CatalogCreateRequest;
import hust.hoangson.product.request.CatalogSearchRequest;
import hust.hoangson.product.request.CatalogUpdateRequest;
import hust.hoangson.product.response.BaseResponse;
import hust.hoangson.product.service.ProductCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product/catalogs")
public class ProductCatalogController {

    private final ProductCatalogService catalogService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CatalogCreateRequest catalog) {
        return ResponseEntity.ok(BaseResponse.success(catalogService.create(catalog)));
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchCatalog(@RequestBody CatalogSearchRequest req) {
        return ResponseEntity.ok(BaseResponse.success(catalogService.search(req)));
    }

    @PutMapping("/{catalogId}")
    public ResponseEntity<?> update(@PathVariable String catalogId, @RequestBody CatalogUpdateRequest req) {
        return ResponseEntity.ok(BaseResponse.success(catalogService.update(catalogId, req)));
    }

    @DeleteMapping("/{catalogId}")
    public ResponseEntity<?> delete(@PathVariable String catalogId) {
        return ResponseEntity.ok(BaseResponse.success(
                "Catalog deleted successfully:" + catalogService.deleteCatalog(catalogId)));
    }


    @PostMapping("/{catalogId}/images")
    public ResponseEntity<?> uploadImage(
            @PathVariable String catalogId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "isPrimary", defaultValue = "false") boolean isPrimary) {
        return ResponseEntity.ok(BaseResponse.success(catalogService.uploadCatalogImage(catalogId, file, isPrimary)));
    }

    @GetMapping("/{catalogId}/images")
    public ResponseEntity<?> getImages(@PathVariable String catalogId) {
        return ResponseEntity.ok(BaseResponse.success(catalogService.getCatalogImages(catalogId)));
    }

    @DeleteMapping("/{catalogId}/images/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable String catalogId ,@PathVariable UUID imageId) {
        return ResponseEntity.ok(BaseResponse.success(catalogService.deleteImage(catalogId, imageId)));
    }
}
