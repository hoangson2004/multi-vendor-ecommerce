package hust.hoangson.order.response;

import hust.hoangson.order.domain.entity.CartEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private String userId;
    private List<CartItemResponse> items;
    private BigDecimal totalPrice;

    public static CartResponse of(CartEntity entity) {

        CartResponse response = new CartResponse();
        response.userId = entity.getUserId();

        if (entity.getItems() != null) {
            response.items = entity.getItems()
                    .stream()
                    .map(CartItemResponse::of)
                    .collect(Collectors.toList());
            response.totalPrice = response.items.stream()
                    .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        return response;
    }
}

