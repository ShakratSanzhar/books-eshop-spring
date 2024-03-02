package com.shakratsanzhar.mapper;

import com.shakratsanzhar.domain.dto.CartReadDto;
import com.shakratsanzhar.domain.entity.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartReadMapper implements Mapper<Cart, CartReadDto> {

    private final UserReadMapper userReadMapper;

    @Override
    public CartReadDto map(Cart object) {
        return new CartReadDto(
                object.getId(),
                userReadMapper.map(object.getUserInCart()),
                object.getPrice()
        );
    }
}
