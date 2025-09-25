package hust.hoangson.product.repository;

import hust.hoangson.product.domain.entity.ProductImageEntity;
import hust.hoangson.product.domain.entity.VariantImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface VariantImageRepository extends JpaRepository<VariantImageEntity, UUID> {
    @Query("SELECT DISTINCT i FROM VariantImageEntity i " +
            "WHERE i.variantUuid = (SELECT v.id FROM ProductVariantEntity v WHERE v.variantId = :variantId)")
    List<VariantImageEntity> findByVariantId(@Param("variantId") String variantId);

    @Modifying
    @Transactional
    @Query("DELETE FROM VariantImageEntity i WHERE i.imageId = :imageId")
    int deleteImageById(@Param("imageId") UUID imageId);
}
