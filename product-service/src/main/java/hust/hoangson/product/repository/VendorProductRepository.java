package hust.hoangson.product.repository;

import hust.hoangson.product.domain.entity.VendorProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VendorProductRepository extends JpaRepository<VendorProductEntity, UUID> {

    Optional<VendorProductEntity> findByVendorProductId(String vendorProductId);

    int deleteByVendorProductId(String vendorProductId);

    @Query("SELECT vp FROM VendorProductEntity vp " +
            "JOIN vp.productCatalog c " +
            "WHERE (:vendorProductId IS NULL OR vp.vendorProductId = :vendorProductId) " +
            "AND (:vendorId IS NULL OR vp.vendorId = :vendorId) " +
            "AND (:catalogId IS NULL OR c.catalogId = :catalogId) " +
            "AND (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:description IS NULL OR LOWER(c.description) LIKE LOWER(CONCAT('%', :description, '%'))) " +
            "AND (:brand IS NULL OR LOWER(c.brand) LIKE LOWER(CONCAT('%', :brand, '%'))) " +
            "AND (:priceFrom IS NULL OR vp.price >= :priceFrom) " +
            "AND (:priceTo IS NULL OR vp.price <= :priceTo) " +
            "AND (:status IS NULL OR vp.status = :status)")
    Page<VendorProductEntity> searchVendorProducts(
            String vendorProductId,
            String vendorId,
            String catalogId,
            String name,
            String description,
            String brand,
            BigDecimal priceFrom,
            BigDecimal priceTo,
            Integer status,
            Pageable pageable
    );
}
