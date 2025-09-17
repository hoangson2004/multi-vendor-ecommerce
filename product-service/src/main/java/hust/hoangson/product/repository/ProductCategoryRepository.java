package hust.hoangson.product.repository;

import hust.hoangson.product.domain.entity.ProductCategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, String> {

    Optional<ProductCategoryEntity> findByCategoryId(String categoryId);

    @Query("SELECT c FROM ProductCategoryEntity c " +
            "WHERE (:categoryId IS NULL OR :categoryId = c.categoryId) " +
            "AND (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:slug IS NULL OR LOWER(c.slug) LIKE LOWER(CONCAT('%', :slug, '%'))) " +
            "AND c.isActive = true")
    Page<ProductCategoryEntity> searchCategories(String categoryId, String name, String slug, Pageable pageable);

    @Modifying
    @Query("UPDATE ProductCategoryEntity c SET c.isActive = false WHERE c.categoryId = :categoryId")
    int softDeleteByCategoryId(String categoryId);

    boolean existsByParent_CategoryId(String parentId);
}
