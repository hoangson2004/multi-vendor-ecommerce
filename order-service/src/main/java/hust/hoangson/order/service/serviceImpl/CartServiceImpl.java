package hust.hoangson.order.service.serviceImpl;

import hust.hoangson.order.client.ProductClient;
import hust.hoangson.order.domain.dto.ProductVariantDTO;
import hust.hoangson.order.domain.entity.CartEntity;
import hust.hoangson.order.domain.entity.CartItemEntity;
import hust.hoangson.order.repository.CartRepository;
import hust.hoangson.order.request.AddToCartRequest;
import hust.hoangson.order.response.BaseResponse;
import hust.hoangson.order.response.CartResponse;
import hust.hoangson.order.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductClient productClient;

    @Override
    public CartResponse getCart(String userId) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setUserId(userId);
                    return cartRepository.save(newCart);
                });
        return cartMapper.toResponse(cart);
    }

    @Override
    public CartResponse addToCart(String userId, AddToCartRequest request) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setUserId(userId);
                    return cartRepository.save(newCart);
                });

        BaseResponse<ProductVariantDTO> response =
                productClient.getByVariantId(request.getVariantId());

        if (response == null || !response.isSuccess() || response.getData() == null) {
            throw new RuntimeException();
        }

        ProductVariantDTO variant = response.getData();

        CartItemEntity item = new CartItemEntity();
        item.setCart(cart);
        item.setProductId(variant.getProductId());
        item.setProductName(variant.getProductName());
        item.setProductUrl(variant.getProductUrl());
        item.setVariantId(request.getVariantId());
        item.setQuantity(request.getQuantity());
        item.setPrice(variant.getPrice()); // snapshot giá tại thời điểm add

        cart.getItems().add(item);
        cartRepository.save(cart);

        return cartMapper.toResponse(cart);
    }

    @Override
    public CartResponse removeItem(String userId, String cartItemId) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().removeIf(i -> i.getId().toString().equals(cartItemId));
        cartRepository.save(cart);

        return cartMapper.toResponse(cart);
    }

    @Override
    public void clearCart(String userId) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

}