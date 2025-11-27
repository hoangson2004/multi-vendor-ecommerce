package hust.hoangson.order.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items", schema = "order_schema")
@Data
public class OrderItemEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_uuid")
    private OrderEntity order;

    @Column(name = "variant_id", nullable = false)
    private String variantId;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
