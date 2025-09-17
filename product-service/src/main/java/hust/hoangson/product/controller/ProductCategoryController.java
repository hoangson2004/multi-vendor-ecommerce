package hust.hoangson.product.controller;

import hust.hoangson.product.request.CategoryCreateRequest;
import hust.hoangson.product.request.CategorySearchRequest;
import hust.hoangson.product.request.CategoryUpdateRequest;
import hust.hoangson.product.response.BaseResponse;
import hust.hoangson.product.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product/category")
@RequiredArgsConstructor
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    @PostMapping("/search")
    public ResponseEntity<?> searchCategory(@RequestBody CategorySearchRequest req){
        return ResponseEntity.ok(BaseResponse.success(productCategoryService.search(req)));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestBody CategoryCreateRequest req){
        return ResponseEntity.ok(BaseResponse.success(productCategoryService.create(req)));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable String categoryId, @RequestBody CategoryUpdateRequest req){
        return ResponseEntity.ok(BaseResponse.success(productCategoryService.update(categoryId, req)));
    }

    @PutMapping("/delete/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable String categoryId){
        return ResponseEntity.ok(BaseResponse.success(productCategoryService.delete(categoryId)));
    }
}
