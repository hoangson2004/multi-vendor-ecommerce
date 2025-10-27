package hust.hoangson.order.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "cart_items", schema = "order_schema")
@Data
public class CartItemEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_uuid", nullable = false)
    private CartEntity cart;

    @Column(name = "vendor_product_id")
    private String vendorProductId;

    @Column(name = "vendor_product_name")
    private String vendorProductName;

    @Column(name = "vendor_product_url")
    private String vendorProductUrl;

    @Column(name = "variant_id", nullable = false)
    private String variantId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
}

