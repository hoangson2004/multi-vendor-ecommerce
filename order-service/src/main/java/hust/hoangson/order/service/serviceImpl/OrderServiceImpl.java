package hust.hoangson.order.service.serviceImpl;

import hust.hoangson.clients.dto.ProductVariantDTO;
import hust.hoangson.order.domain.entity.*;
import hust.hoangson.order.domain.enums.OrderStatus;
import hust.hoangson.order.domain.enums.PaymentStatus;
import hust.hoangson.order.messaging.producer.OrderEventPublisher;
import hust.hoangson.order.repository.CartRepository;
import hust.hoangson.order.repository.OrderHistoryRepository;
import hust.hoangson.order.repository.OrderItemRepository;
import hust.hoangson.order.repository.OrderRepository;
import hust.hoangson.order.request.PlaceOrderRequest;
import hust.hoangson.order.response.OrderResponse;
import hust.hoangson.order.service.OrderService;
import hust.hoangson.order.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final ProductService productService;
    private final OrderEventPublisher orderEventPublisher;

    @Override
    public List<OrderResponse> placeOrder(String userId, PlaceOrderRequest request) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 1) Map vendorId -> list cart items
        Map<String, List<CartItemEntity>> itemsByVendor = new HashMap<>();

        for (CartItemEntity item : cart.getItems()) {
            ProductVariantDTO variant = productService.getVariantById(item.getVariantId());
            if (variant == null) {
                throw new RuntimeException("Variant not found: " + item.getVariantId());
            }
            if (variant.getStockQuantity() != null &&
                    variant.getStockQuantity() < item.getQuantity()) {
                throw new RuntimeException("Not enough stock for variant " + item.getVariantId());
            }

            // cập nhật lại giá
            item.setPrice(variant.getPrice());

            String vendorId = variant.getVendorId();
            itemsByVendor.computeIfAbsent(vendorId, k -> new ArrayList<>())
                    .add(item);
        }

        List<OrderResponse> result = new ArrayList<>();

        for (Map.Entry<String, List<CartItemEntity>> entry : itemsByVendor.entrySet()) {
            String vendorId = entry.getKey();
            List<CartItemEntity> vendorItems = entry.getValue();

            BigDecimal total = BigDecimal.ZERO;

            // Tạo order cho vendor này
            OrderEntity order = new OrderEntity();
            order.setUserId(userId);
            order.setVendorId(vendorId);
            order.setStatus(OrderStatus.PENDING.getCode());
            order.setPaymentStatus(PaymentStatus.UNPAID.getCode());
            order.setCreatedAt(LocalDateTime.now());
            order.setUpdatedAt(LocalDateTime.now());
            order.setOrderCode(generateOrderCode());

            // Tạo order items
            List<OrderItemEntity> orderItems = new ArrayList<>();
            for (CartItemEntity cartItem : vendorItems) {
                OrderItemEntity item = new OrderItemEntity();
                item.setVariantId(cartItem.getVariantId());
                item.setProductId(cartItem.getVendorProductId());
                item.setProductName(cartItem.getVendorProductName());
                item.setQuantity(cartItem.getQuantity());
                item.setPrice(cartItem.getPrice());
                BigDecimal subtotal = cartItem.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()));
                item.setSubtotal(subtotal);
                item.setOrder(order);
                total = total.add(subtotal);
                orderItems.add(item);
            }

            order.setTotalAmount(total);
            order.setItems(orderItems);
            order = orderRepository.save(order);

            // Lịch sử đơn hàng
            OrderHistoryEntity history = new OrderHistoryEntity();
            history.setOrderUuid(order.getId());
            history.setStatus(order.getStatus());
            history.setNote(request.getNote() != null ? request.getNote() : "Order created");
            history.setCreatedAt(LocalDateTime.now());

            orderHistoryRepository.save(history);

            // (optional) publish Kafka OrderCreatedEvent

            result.add(OrderResponse.of(order, orderItems));
        }

        cart.getItems().clear();
        cartRepository.save(cart);

        return result;
    }

    private String generateOrderCode() {
        String code;
        do {
            String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String randomPart = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
            code = "ORD-" + datePart + "-" + randomPart;
        } while (orderRepository.existsByOrderCode(code));
        return code;
    }

}
