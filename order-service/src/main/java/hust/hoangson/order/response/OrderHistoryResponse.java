package hust.hoangson.order.response;

import hust.hoangson.order.domain.entity.OrderHistoryEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderHistoryResponse {
    private UUID id;
    private Integer status;
    private String note;
    private LocalDateTime createdAt;

    public static OrderHistoryResponse of(OrderHistoryEntity entity) {
        OrderHistoryResponse dto = new OrderHistoryResponse();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setNote(entity.getNote());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
