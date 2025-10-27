package hust.hoangson.order.service.serviceImpl;

import hust.hoangson.clients.dto.ProductVariantDTO;
import hust.hoangson.clients.feign.ProductClient;
import hust.hoangson.order.domain.entity.CartEntity;
import hust.hoangson.order.domain.entity.CartItemEntity;
import hust.hoangson.order.repository.CartRepository;
import hust.hoangson.order.request.AddToCartRequest;
import hust.hoangson.order.response.BaseResponse;
import hust.hoangson.order.response.CartResponse;
import hust.hoangson.order.service.CartService;
import hust.hoangson.order.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {


    private final CartRepository cartRepository;
    private final ProductService productService;

    @Override
    public CartResponse getCart(String userId) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setUserId(userId);
                    return cartRepository.save(newCart);
                });
        return CartResponse.of(cart);
    }

    @Transactional
    @Override
    public CartResponse addToCart(String userId, AddToCartRequest request) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setUserId(userId);
                    return cartRepository.save(newCart);
                });

        ProductVariantDTO variant = productService.getVariantById(request.getVariantId());
        if (variant == null) {
            throw new RuntimeException("Không thể lấy thông tin sản phẩm từ Product Service");
        }

        CartItemEntity item = new CartItemEntity();
        item.setCart(cart);
        item.setVariantId(request.getVariantId());
        item.setQuantity(request.getQuantity());
        item.setPrice(variant.getPrice());
        cart.getItems().add(item);

        cartRepository.save(cart);
        return CartResponse.of(cart);
    }

    @Override
    public CartResponse removeItem(String userId, String cartItemId) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().removeIf(i -> i.getId().toString().equals(cartItemId));
        cartRepository.save(cart);

        return CartResponse.of(cart);
    }

    @Override
    public void clearCart(String userId) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

}