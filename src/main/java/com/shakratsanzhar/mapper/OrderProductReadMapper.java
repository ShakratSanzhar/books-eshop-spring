package com.shakratsanzhar.mapper;

import com.shakratsanzhar.domain.dto.OrderProductReadDto;
import com.shakratsanzhar.domain.dto.OrderReadDto;
import com.shakratsanzhar.domain.dto.ProductReadDto;
import com.shakratsanzhar.domain.entity.OrderProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderProductReadMapper implements Mapper<OrderProduct, OrderProductReadDto> {

    private final OrderReadMapper orderReadMapper;
    private final ProductReadMapper productReadMapper;

    @Override
    public OrderProductReadDto map(OrderProduct object) {
        OrderReadDto order = Optional.ofNullable(object.getOrder())
                .map(orderReadMapper::map)
                .orElse(null);
        ProductReadDto product = Optional.ofNullable(object.getProductInOrder())
                .map(productReadMapper::map)
                .orElse(null);
        return new OrderProductReadDto(
                object.getId(),
                order,
                product,
                object.getQuantity()
        );
    }
}
