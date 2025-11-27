package hust.hoangson.common.kafka.event.order;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    private String orderId;
    private String userId;
    private BigDecimal totalAmount;
    private Integer status;
    private Integer paymentStatus;
}
