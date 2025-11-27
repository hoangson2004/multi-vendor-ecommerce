package hust.hoangson.order.service;

import hust.hoangson.order.request.OrderSearchRequest;
import hust.hoangson.order.request.PlaceOrderRequest;
import hust.hoangson.order.response.OrderResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    Page<OrderResponse> searchOrders(String userId, OrderSearchRequest request);

    List<OrderResponse> placeOrder(String userId, PlaceOrderRequest request);
}
