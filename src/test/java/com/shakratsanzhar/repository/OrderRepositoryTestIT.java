package com.shakratsanzhar.repository;

import com.shakratsanzhar.IntegrationTestBase;
import com.shakratsanzhar.domain.entity.Order;
import com.shakratsanzhar.domain.entity.OrderProduct;
import com.shakratsanzhar.domain.enums.OrderStatus;
import com.shakratsanzhar.utils.TestEntityBuilder;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class OrderRepositoryTestIT extends IntegrationTestBase {

    private static final Integer OLD_PRICE = 999;
    private static final Integer NEW_PRICE = 888;
    private static final Long EXISTING_PRODUCT_ID1 = 1L;
    private static final Long EXISTING_PRODUCT_ID2 = 2L;
    private static final Integer QUANTITY1 = 1;
    private static final Integer QUANTITY2 = 2;

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Test
    void saveNewOrder() {
        var user = TestEntityBuilder.createUser();
        userRepository.save(user);
        var order = Order.builder()
                .userInOrder(user)
                .price(0)
                .status(OrderStatus.NOT_PAID)
                .createdAt(LocalDateTime.now())
                .build();

        orderRepository.save(order);

        assertThat(order.getId()).isNotNull();
    }

    @Test
    void deleteExistingOrder() {
        var user = TestEntityBuilder.createUser();
        userRepository.save(user);
        var order = Order.builder()
                .userInOrder(user)
                .price(NEW_PRICE)
                .status(OrderStatus.NOT_PAID)
                .createdAt(LocalDateTime.now())
                .build();
        orderRepository.save(order);

        orderRepository.deleteById(order.getId());
        var actualCart = orderRepository.findById(order.getId());

        assertThat(actualCart).isEmpty();
    }

    @Test
    void updateExistingOrder() {
        var user = TestEntityBuilder.createUser();
        userRepository.save(user);
        var order = Order.builder()
                .userInOrder(user)
                .price(OLD_PRICE)
                .status(OrderStatus.NOT_PAID)
                .createdAt(LocalDateTime.now())
                .build();
        orderRepository.save(order);
        order.setPrice(NEW_PRICE);

        var actualCart = orderRepository.findById(order.getId());

        assertThat(actualCart).isNotEmpty();
        assertThat(actualCart.get().getPrice()).isEqualTo(NEW_PRICE);
    }

    @Test
    void getExistingOrder() {
        var user = TestEntityBuilder.createUser();
        userRepository.save(user);
        var order = Order.builder()
                .userInOrder(user)
                .price(OLD_PRICE)
                .status(OrderStatus.NOT_PAID)
                .createdAt(LocalDateTime.now())
                .build();
        orderRepository.save(order);

        var actualCart = orderRepository.findById(order.getId());

        assertThat(actualCart).isNotEmpty();
        assertThat(actualCart.get().getId()).isNotNull();
    }

    @Test
    void addProductToOrder() {
        var user = TestEntityBuilder.createUser();
        userRepository.save(user);
        var product = productRepository.findById(EXISTING_PRODUCT_ID1);
        var order = Order.builder()
                .userInOrder(user)
                .price(OLD_PRICE)
                .status(OrderStatus.NOT_PAID)
                .createdAt(LocalDateTime.now())
                .build();
        orderRepository.save(order);
        var orderProduct = OrderProduct.builder()
                .quantity(QUANTITY1)
                .build();
        orderProduct.setOrder(order);
        orderProduct.setProduct(product.get());

        var actualCartProduct = orderRepository.addProductToOrder(orderProduct);

        assertThat(actualCartProduct).isNotNull();
        assertThat(actualCartProduct.getOrder()).isEqualTo(order);
        assertThat(actualCartProduct.getProductInOrder()).isEqualTo(product.get());
        assertThat(actualCartProduct.getId()).isNotNull();
        assertThat(actualCartProduct.getQuantity()).isEqualTo(QUANTITY1);
    }

    @Test
    void findAllProductsInOrder() {
        var user = TestEntityBuilder.createUser();
        userRepository.save(user);
        var product1 = productRepository.findById(EXISTING_PRODUCT_ID1);
        var product2 = productRepository.findById(EXISTING_PRODUCT_ID2);
        var order = Order.builder()
                .userInOrder(user)
                .price(0)
                .status(OrderStatus.NOT_PAID)
                .createdAt(LocalDateTime.now())
                .build();
        orderRepository.save(order);
        var orderProduct1 = OrderProduct.builder()
                .quantity(QUANTITY1)
                .build();
        orderProduct1.setOrder(order);
        orderProduct1.setProduct(product1.get());
        var orderProduct2 = OrderProduct.builder()
                .quantity(QUANTITY2)
                .build();
        orderProduct2.setOrder(order);
        orderProduct2.setProduct(product2.get());
        orderRepository.addProductToOrder(orderProduct1);
        orderRepository.addProductToOrder(orderProduct2);

        var actualProductsInCart = orderRepository.findAllProductsInOrder(order.getId());

        assertThat(actualProductsInCart).isNotNull();
        assertThat(actualProductsInCart).hasSize(2);
    }
}
