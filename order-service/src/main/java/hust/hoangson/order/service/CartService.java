package hust.hoangson.order.service;

import hust.hoangson.order.request.AddToCartRequest;
import hust.hoangson.order.response.CartResponse;

public interface CartService {
    CartResponse getCart(String userId);
    CartResponse addToCart(String userId, AddToCartRequest request);
    CartResponse removeItem(String userId, String cartItemId);
    void clearCart(String userId);
}
