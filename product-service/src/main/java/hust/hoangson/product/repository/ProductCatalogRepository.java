package hust.hoangson.product.repository;

import hust.hoangson.product.domain.entity.ProductCatalogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface ProductCatalogRepository extends JpaRepository<ProductCatalogEntity, UUID> {
    @Query("SELECT DISTINCT c FROM ProductCatalogEntity c " +
            "WHERE (:catalogId IS NULL OR c.catalogId = :catalogId) " +
            "AND (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:description IS NULL OR LOWER(c.description) LIKE LOWER(CONCAT('%', :description, '%'))) " +
            "AND (:brand IS NULL OR LOWER(c.brand) LIKE LOWER(CONCAT('%', :brand, '%')))")
    Page<ProductCatalogEntity> searchCatalogs(@Param("catalogId") String catalogId,
                                              @Param("name") String name,
                                              @Param("description") String description,
                                              @Param("brand") String brand,
                                              Pageable pageable);

    Optional<ProductCatalogEntity> findByCatalogId(String catalogId);

    @Transactional
    @Modifying
    @Query("UPDATE ProductCatalogEntity c SET c.isActive = false WHERE c.catalogId = :catalogId")
    int updateIsActiveFalse(@Param("catalogId") String catalogId);
}
