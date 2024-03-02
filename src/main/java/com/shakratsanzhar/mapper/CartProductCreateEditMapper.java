package com.shakratsanzhar.mapper;

import com.shakratsanzhar.domain.dto.CartProductCreateEditDto;
import com.shakratsanzhar.domain.entity.Cart;
import com.shakratsanzhar.domain.entity.CartProduct;
import com.shakratsanzhar.domain.entity.Product;
import com.shakratsanzhar.repository.CartRepository;
import com.shakratsanzhar.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartProductCreateEditMapper implements Mapper<CartProductCreateEditDto, CartProduct> {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    public CartProduct map(CartProductCreateEditDto object) {
        CartProduct cartProduct = new CartProduct();
        copy(object, cartProduct);
        return cartProduct;
    }

    @Override
    public CartProduct map(CartProductCreateEditDto fromObject, CartProduct toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(CartProductCreateEditDto object, CartProduct cartProduct) {
        cartProduct.setCart(getCart(object.getCartId()));
        cartProduct.setProductInCart(getProduct(object.getProductId()));
        cartProduct.setQuantity(object.getQuantity());
    }

    private Cart getCart(Long cartId) {
        return Optional.ofNullable(cartId)
                .flatMap(cartRepository::findById)
                .orElse(null);
    }

    private Product getProduct(Long productId) {
        return Optional.ofNullable(productId)
                .flatMap(productRepository::findById)
                .orElse(null);
    }
}
