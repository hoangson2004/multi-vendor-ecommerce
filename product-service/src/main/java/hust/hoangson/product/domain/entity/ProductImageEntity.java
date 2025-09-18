package hust.hoangson.product.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "product_images", schema = "product_schema")
@Getter
@Setter
public class ProductImageEntity {

    @Id
    @Column(name = "image_id", nullable = false)
    private UUID imageId;

    @Column(name = "catalog_uuid", nullable = false)
    private UUID catalogUuid;

    @Column(name = "url", nullable = false, length = 500)
    private String url;

    @Column(name = "is_primary")
    private Boolean isPrimary = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
