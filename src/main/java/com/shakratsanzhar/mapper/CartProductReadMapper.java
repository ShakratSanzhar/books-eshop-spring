package com.shakratsanzhar.mapper;

import com.shakratsanzhar.domain.dto.CartProductReadDto;
import com.shakratsanzhar.domain.dto.CartReadDto;
import com.shakratsanzhar.domain.dto.ProductReadDto;
import com.shakratsanzhar.domain.entity.CartProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartProductReadMapper implements Mapper<CartProduct, CartProductReadDto> {

    private final CartReadMapper cartReadMapper;
    private final ProductReadMapper productReadMapper;

    @Override
    public CartProductReadDto map(CartProduct object) {
        CartReadDto cart = Optional.ofNullable(object.getCart())
                .map(cartReadMapper::map)
                .orElse(null);
        ProductReadDto product = Optional.ofNullable(object.getProductInCart())
                .map(productReadMapper::map)
                .orElse(null);
        return new CartProductReadDto(
                object.getId(),
                cart,
                product,
                object.getQuantity()
        );
    }
}
