package com.shakratsanzhar.mapper;

import com.shakratsanzhar.domain.dto.OrderProductCreateEditDto;
import com.shakratsanzhar.domain.entity.Order;
import com.shakratsanzhar.domain.entity.OrderProduct;
import com.shakratsanzhar.domain.entity.Product;
import com.shakratsanzhar.repository.OrderRepository;
import com.shakratsanzhar.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderProductCreateEditMapper implements Mapper<OrderProductCreateEditDto, OrderProduct> {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderProduct map(OrderProductCreateEditDto object) {
        OrderProduct orderProduct = new OrderProduct();
        copy(object, orderProduct);
        return orderProduct;
    }

    @Override
    public OrderProduct map(OrderProductCreateEditDto fromObject, OrderProduct toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(OrderProductCreateEditDto object, OrderProduct orderProduct) {
        orderProduct.setOrder(getOrder(object.getOrderId()));
        orderProduct.setProductInOrder(getProduct(object.getProductId()));
        orderProduct.setQuantity(object.getQuantity());
    }

    private Order getOrder(Long orderId) {
        return Optional.ofNullable(orderId)
                .flatMap(orderRepository::findById)
                .orElse(null);
    }

    private Product getProduct(Long productId) {
        return Optional.ofNullable(productId)
                .flatMap(productRepository::findById)
                .orElse(null);
    }
}
