package hust.hoangson.order.controller;

import hust.hoangson.order.request.AddToCartRequest;
import hust.hoangson.order.response.BaseResponse;
import hust.hoangson.order.response.CartResponse;
import hust.hoangson.order.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<?> getCart(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(BaseResponse.success(cartService.getCart(userId)));
    }

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody AddToCartRequest request
    ) {
        return ResponseEntity.ok(BaseResponse.success(cartService.addToCart(userId, request)));
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> removeItem(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String cartItemId
    ) {
        return ResponseEntity.ok(BaseResponse.success(cartService.removeItem(userId, cartItemId)));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(@RequestHeader("X-User-Id") String userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok(BaseResponse.success(null));
    }
}
