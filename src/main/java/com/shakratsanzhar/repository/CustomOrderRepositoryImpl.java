package com.shakratsanzhar.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.shakratsanzhar.domain.entity.OrderProduct;
import com.shakratsanzhar.domain.entity.Product;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.shakratsanzhar.domain.entity.QOrderProduct.orderProduct;

@RequiredArgsConstructor
public class CustomOrderRepositoryImpl implements CustomOrderRepository {

    private final EntityManager entityManager;

    @Override
    public OrderProduct addProductToOrder(OrderProduct orderProduct) {
        entityManager.persist(orderProduct);
        return orderProduct;
    }

    @Override
    public List<Product> findAllProductsInOrder(Long id) {
        List<OrderProduct> orderProducts = new JPAQuery<Product>(entityManager)
                .select(orderProduct)
                .from(orderProduct)
                .where(orderProduct.order.id.eq(id))
                .fetch();
        return orderProducts.stream().map(OrderProduct::getProductInOrder).toList();
    }

    @Override
    public Map<Product, Integer> findAllProductsWithQuantities(Long id) {
        List<OrderProduct> orderProducts = new JPAQuery<Product>(entityManager)
                .select(orderProduct)
                .from(orderProduct)
                .where(orderProduct.order.id.eq(id))
                .fetch();
        return orderProducts.stream().collect(Collectors.toMap(OrderProduct::getProductInOrder, OrderProduct::getQuantity));
    }
}
