package com.shakratsanzhar.repository;

import com.shakratsanzhar.domain.entity.CartProduct;
import com.shakratsanzhar.domain.entity.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CustomCartRepository {

    CartProduct addProductToCart(CartProduct cartProduct);

    List<Product> findAllProductsInCart(Long id);

    Map<Product,Integer> findAllProductsWithQuantities(Long id);

    Optional<CartProduct> findCartProductByCartIdAndProductId(Long cartId, Long productId);
}
