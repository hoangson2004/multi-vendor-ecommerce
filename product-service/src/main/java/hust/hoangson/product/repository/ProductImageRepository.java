package hust.hoangson.product.repository;

import hust.hoangson.product.domain.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImageEntity, UUID> {
    @Query("SELECT DISTINCT i FROM ProductImageEntity i " +
            "WHERE i.catalogUuid = (SELECT c.id FROM ProductCatalogEntity c WHERE c.catalogId = :catalogId)")
    List<ProductImageEntity> findByCatalogId(@Param("catalogId") String catalogId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductImageEntity i WHERE i.imageId = :imageId")
    int deleteImageById(@Param("imageId") UUID imageId);

}

