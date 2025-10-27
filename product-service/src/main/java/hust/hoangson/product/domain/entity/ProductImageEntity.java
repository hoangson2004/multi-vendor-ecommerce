package hust.hoangson.product.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "product_images", schema = "product_schema")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "image_id")
    private UUID imageId;

    @Column(name = "owner_id", nullable = false)
    private String ownerId;

    @Column(name = "owner_type", nullable = false)
    private String ownerType;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "is_primary")
    private Boolean isPrimary = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
