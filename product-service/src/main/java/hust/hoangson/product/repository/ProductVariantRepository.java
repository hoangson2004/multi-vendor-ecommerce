package hust.hoangson.product.repository;

import hust.hoangson.product.domain.entity.ProductVariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity, UUID> {
    @Query("SELECT v FROM ProductVariantEntity v JOIN FETCH v.vendorProduct p " +
            "WHERE p.vendorProductId = :vendorProductId")
    List<ProductVariantEntity> findByVendorProductId(@Param("vendorProductId") String vendorProductId);

    @Query("SELECT v FROM ProductVariantEntity v " +
            "WHERE v.variantId = :variantId")
    Optional<ProductVariantEntity> findByVariantId(@Param("variantId") String variantId);

    int deleteByVariantId(String variantId);
}

