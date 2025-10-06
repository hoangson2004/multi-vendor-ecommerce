package hust.hoangson.order.response;

import java.math.BigDecimal;
import java.util.List;

public class CartResponse {
    private String userId;
    private List<CartItemResponse> items;
    private BigDecimal totalPrice;
}

