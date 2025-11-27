package hust.hoangson.order.repository;

import hust.hoangson.order.domain.entity.OrderHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderHistoryRepository extends JpaRepository<OrderHistoryEntity, UUID> {

    List<OrderHistoryEntity> findByOrderUuidIn(List<UUID> orderIds);
}