package hust.hoangson.product.repository;

import hust.hoangson.product.domain.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductImageRepository extends JpaRepository<ProductImageEntity, UUID> {
    List<ProductImageEntity> findByOwnerIdAndOwnerType(String ownerId, String ownerType);

    List<ProductImageEntity> findByOwnerId(String ownerId);

    ProductImageEntity findByImageId(UUID imageId);

    @Query(
            value = "SELECT i.url FROM product_schema.product_images i " +
                    "WHERE i.owner_id = :ownerId AND i.is_primary = true " +
                    "ORDER BY i.created_at DESC LIMIT 1",
            nativeQuery = true
    )
    Optional<String> findPrimaryImageUrl(@Param("ownerId") String ownerId);


    @Modifying
    @Transactional
    @Query("DELETE FROM ProductImageEntity i WHERE i.imageId = :imageId")
    int deleteImageById(@Param("imageId") UUID imageId);

    @Modifying
    @Query("UPDATE ProductImageEntity p SET p.isPrimary = false WHERE p.ownerId = :ownerId AND p.ownerType = :ownerType")
    void resetPrimaryImages(String ownerId, String ownerType);

}
