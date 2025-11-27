package hust.hoangson.order.controller;

import hust.hoangson.order.request.OrderSearchRequest;
import hust.hoangson.order.request.PlaceOrderRequest;
import hust.hoangson.order.response.BaseResponse;
import hust.hoangson.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/search")
    public ResponseEntity<?> searchOrders(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody OrderSearchRequest request
    ) {
        return ResponseEntity.ok(
                BaseResponse.success(orderService.searchOrders(userId, request))
        );
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> placeOrder(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody PlaceOrderRequest request
    ) {
        return ResponseEntity.ok(
                BaseResponse.success(orderService.placeOrder(userId, request))
        );
    }
}
