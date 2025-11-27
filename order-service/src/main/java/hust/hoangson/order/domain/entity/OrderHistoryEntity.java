package hust.hoangson.order.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "order_history", schema = "order_schema")
@Data
public class OrderHistoryEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "order_uuid", nullable = false)
    private UUID orderUuid;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "note")
    private String note;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}

