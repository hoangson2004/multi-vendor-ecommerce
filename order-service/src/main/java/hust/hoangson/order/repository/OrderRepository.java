package hust.hoangson.order.repository;

import hust.hoangson.order.domain.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findByUserId(String userId);
    boolean existsByOrderCode(String orderCode);

}
