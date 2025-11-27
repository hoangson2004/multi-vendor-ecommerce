package hust.hoangson.order.response;

import hust.hoangson.order.domain.entity.OrderEntity;
import hust.hoangson.order.domain.entity.OrderItemEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private String id;
    private String userId;
    private Integer status;
    private Integer paymentStatus;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;

    public static OrderResponse of(OrderEntity order, List<OrderItemEntity> items) {
        OrderResponse r = new OrderResponse();
        r.id = order.getId().toString();
        r.userId = order.getUserId();
        r.status = order.getStatus();
        r.paymentStatus = order.getPaymentStatus();
        r.totalAmount = order.getTotalAmount();
        r.createdAt = order.getCreatedAt();
        r.items = items.stream().map(OrderItemResponse::of).toList();
        return r;
    }
}
