package com.shakratsanzhar.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.shakratsanzhar.domain.entity.CartProduct;
import com.shakratsanzhar.domain.entity.Product;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.shakratsanzhar.domain.entity.QCartProduct.cartProduct;

@RequiredArgsConstructor
public class CustomCartRepositoryImpl implements CustomCartRepository {

    private final EntityManager entityManager;

    @Override
    public CartProduct addProductToCart(CartProduct cartProduct) {
        entityManager.persist(cartProduct);
        return cartProduct;
    }

    @Override
    public List<Product> findAllProductsInCart(Long id) {
        List<CartProduct> cartProducts = new JPAQuery<Product>(entityManager)
                .select(cartProduct)
                .from(cartProduct)
                .where(cartProduct.cart.id.eq(id))
                .fetch();
        return cartProducts.stream().map(CartProduct::getProductInCart).toList();
    }

    @Override
    public Map<Product, Integer> findAllProductsWithQuantities(Long id) {
        List<CartProduct> cartProducts = new JPAQuery<Product>(entityManager)
                .select(cartProduct)
                .from(cartProduct)
                .where(cartProduct.cart.id.eq(id))
                .fetch();
        return cartProducts.stream().collect(Collectors.toMap(CartProduct::getProductInCart, CartProduct::getQuantity));
    }

    @Override
    public Optional<CartProduct> findCartProductByCartIdAndProductId(Long cartId, Long productId) {
        return new JPAQuery<CartProduct>(entityManager)
                .select(cartProduct)
                .from(cartProduct)
                .where(cartProduct.cart.id.eq(cartId).and(cartProduct.productInCart.id.eq(productId)))
                .stream().findAny();
    }
}

