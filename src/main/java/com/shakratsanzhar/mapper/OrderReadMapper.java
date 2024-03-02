package com.shakratsanzhar.mapper;

import com.shakratsanzhar.domain.dto.OrderReadDto;
import com.shakratsanzhar.domain.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderReadMapper implements Mapper<Order, OrderReadDto> {

    private final UserReadMapper userReadMapper;

    @Override
    public OrderReadDto map(Order object) {
        return new OrderReadDto(
                object.getId(),
                userReadMapper.map(object.getUserInOrder()),
                object.getStatus(),
                object.getPrice(),
                object.getCreatedAt(),
                object.getClosedAt()
        );
    }
}
