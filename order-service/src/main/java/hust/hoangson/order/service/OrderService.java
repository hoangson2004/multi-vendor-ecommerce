package hust.hoangson.order.service;

import hust.hoangson.order.request.PlaceOrderRequest;
import hust.hoangson.order.response.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> placeOrder(String userId, PlaceOrderRequest request);
}
