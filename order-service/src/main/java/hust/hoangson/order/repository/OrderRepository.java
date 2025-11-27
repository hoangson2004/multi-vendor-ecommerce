package hust.hoangson.order.repository;

import hust.hoangson.order.domain.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findByUserId(String userId);

    boolean existsByOrderCode(String orderCode);

    @Query("""
        SELECT o FROM OrderEntity o
        WHERE o.userId = :userId
          AND (:status IS NULL OR o.status = :status)
        """)
    Page<OrderEntity> searchOrders(
            @Param("userId") String userId,
            @Param("status") Integer status,
            @Param("createdFrom") LocalDateTime createdFrom,
            @Param("createdTo") LocalDateTime createdTo,
            Pageable pageable
    );

}
