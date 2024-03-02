package com.shakratsanzhar.repository;

import com.shakratsanzhar.domain.entity.OrderProduct;
import com.shakratsanzhar.domain.entity.Product;

import java.util.List;
import java.util.Map;

public interface CustomOrderRepository {

    OrderProduct addProductToOrder(OrderProduct orderProduct);

    List<Product> findAllProductsInOrder(Long id);

    Map<Product, Integer> findAllProductsWithQuantities(Long id);
}
