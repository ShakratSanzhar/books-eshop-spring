package com.shakratsanzhar.repository;

import com.shakratsanzhar.IntegrationTestBase;
import com.shakratsanzhar.domain.entity.Cart;
import com.shakratsanzhar.domain.entity.CartProduct;
import com.shakratsanzhar.utils.TestEntityBuilder;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class CartRepositoryTestIT extends IntegrationTestBase {

    private static final Integer OLD_PRICE = 999;
    private static final Integer NEW_PRICE = 888;
    private static final Long EXISTING_PRODUCT_ID1 = 1L;
    private static final Long EXISTING_PRODUCT_ID2 = 2L;
    private static final Integer QUANTITY1 = 1;
    private static final Integer QUANTITY2 = 2;

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Test
    void saveNewCart() {
        var user = TestEntityBuilder.createUser();
        userRepository.save(user);
        var cart = Cart.builder()
                .userInCart(user)
                .price(0)
                .build();

        cartRepository.save(cart);

        assertThat(cart.getId()).isNotNull();
    }

    @Test
    void deleteExistingCart() {
        var user = TestEntityBuilder.createUser();
        userRepository.save(user);
        var cart = Cart.builder()
                .userInCart(user)
                .price(NEW_PRICE)
                .build();
        cartRepository.save(cart);

        cartRepository.deleteById(cart.getId());
        var actualCart = cartRepository.findById(cart.getId());

        assertThat(actualCart).isEmpty();
    }

    @Test
    void updateExistingCart() {
        var user = TestEntityBuilder.createUser();
        userRepository.save(user);
        var cart = Cart.builder()
                .userInCart(user)
                .price(OLD_PRICE)
                .build();
        cartRepository.save(cart);
        cart.setPrice(NEW_PRICE);

        var actualCart = cartRepository.findById(cart.getId());

        assertThat(actualCart).isNotEmpty();
        assertThat(actualCart.get().getPrice()).isEqualTo(NEW_PRICE);
    }

    @Test
    void getExistingCart() {
        var user = TestEntityBuilder.createUser();
        userRepository.save(user);
        var cart = Cart.builder()
                .userInCart(user)
                .price(OLD_PRICE)
                .build();
        cartRepository.save(cart);

        var actualCart = cartRepository.findById(cart.getId());

        assertThat(actualCart).isNotEmpty();
        assertThat(actualCart.get().getId()).isNotNull();
    }

    @Test
    void addProductToCart() {
        var user = TestEntityBuilder.createUser();
        userRepository.save(user);
        var product = productRepository.findById(EXISTING_PRODUCT_ID1);
        var cart = Cart.builder()
                .userInCart(user)
                .price(OLD_PRICE)
                .build();
        cartRepository.save(cart);
        var cartProduct = CartProduct.builder()
                .quantity(QUANTITY1)
                .build();
        cartProduct.setCart(cart);
        cartProduct.setProduct(product.get());

        var actualCartProduct = cartRepository.addProductToCart(cartProduct);

        assertThat(actualCartProduct).isNotNull();
        assertThat(actualCartProduct.getCart()).isEqualTo(cart);
        assertThat(actualCartProduct.getProductInCart()).isEqualTo(product.get());
        assertThat(actualCartProduct.getId()).isNotNull();
        assertThat(actualCartProduct.getQuantity()).isEqualTo(QUANTITY1);
    }

    @Test
    void findAllProductsInCart() {
        var user = TestEntityBuilder.createUser();
        userRepository.save(user);
        var product1 = productRepository.findById(EXISTING_PRODUCT_ID1);
        var product2 = productRepository.findById(EXISTING_PRODUCT_ID2);
        var cart = Cart.builder()
                .userInCart(user)
                .price(0)
                .build();
        cartRepository.save(cart);
        var cartProduct1 = CartProduct.builder()
                .quantity(QUANTITY1)
                .build();
        cartProduct1.setCart(cart);
        cartProduct1.setProduct(product1.get());
        var cartProduct2 = CartProduct.builder()
                .quantity(QUANTITY2)
                .build();
        cartProduct2.setCart(cart);
        cartProduct2.setProduct(product2.get());
        cartRepository.addProductToCart(cartProduct1);
        cartRepository.addProductToCart(cartProduct2);

        var actualProductsInCart = cartRepository.findAllProductsInCart(cart.getId());

        assertThat(actualProductsInCart).isNotNull();
        assertThat(actualProductsInCart).hasSize(2);
    }

    @Test
    void findAllProductsWithQuantities() {
        var user = TestEntityBuilder.createUser();
        userRepository.save(user);
        var product1 = productRepository.findById(EXISTING_PRODUCT_ID1);
        var product2 = productRepository.findById(EXISTING_PRODUCT_ID2);
        var cart = Cart.builder()
                .userInCart(user)
                .price(0)
                .build();
        cartRepository.save(cart);
        var cartProduct1 = CartProduct.builder()
                .quantity(QUANTITY1)
                .build();
        cartProduct1.setCart(cart);
        cartProduct1.setProduct(product1.get());
        var cartProduct2 = CartProduct.builder()
                .quantity(QUANTITY2)
                .build();
        cartProduct2.setCart(cart);
        cartProduct2.setProduct(product2.get());
        cartRepository.addProductToCart(cartProduct1);
        cartRepository.addProductToCart(cartProduct2);

        var actualMapOfProductsWithQuantities = cartRepository.findAllProductsWithQuantities(cart.getId());

        assertThat(actualMapOfProductsWithQuantities).hasSize(2);
        assertThat(actualMapOfProductsWithQuantities.get(product1.get())).isEqualTo(QUANTITY1);
        assertThat(actualMapOfProductsWithQuantities.get(product2.get())).isEqualTo(QUANTITY2);
    }
}
