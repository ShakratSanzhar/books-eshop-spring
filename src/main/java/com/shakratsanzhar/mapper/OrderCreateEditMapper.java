package com.shakratsanzhar.mapper;

import com.shakratsanzhar.domain.dto.OrderCreateEditDto;
import com.shakratsanzhar.domain.entity.Order;
import com.shakratsanzhar.domain.entity.User;
import com.shakratsanzhar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderCreateEditMapper implements Mapper<OrderCreateEditDto, Order> {

    private final UserRepository userRepository;

    @Override
    public Order map(OrderCreateEditDto object) {
        Order order = new Order();
        copy(object, order);
        return order;
    }

    @Override
    public Order map(OrderCreateEditDto fromObject, Order toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(OrderCreateEditDto object, Order order) {
        order.setUserInOrder(getUser(object.getUserId()));
        order.setStatus(object.getStatus());
        order.setPrice(object.getPrice());
        if (object.getCreatedAt() != null) {
            order.setCreatedAt(object.getCreatedAt());
        }
        order.setClosedAt(object.getClosedAt());
    }

    private User getUser(Long userId) {
        return Optional.ofNullable(userId)
                .flatMap(userRepository::findById)
                .orElse(null);
    }
}
