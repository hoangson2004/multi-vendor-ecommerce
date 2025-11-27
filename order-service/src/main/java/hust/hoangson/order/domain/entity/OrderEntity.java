package hust.hoangson.order.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "orders", schema = "order_schema")
@Data
public class OrderEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "vendor_id")
    private String vendorId;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "status", nullable = false)
    private Integer status = 0;

    @Column(name = "payment_status", nullable = false)
    private Integer paymentStatus = 0;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItemEntity> items = new ArrayList<>();
}

