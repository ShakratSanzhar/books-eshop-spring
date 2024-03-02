package com.shakratsanzhar.mapper;

import com.shakratsanzhar.domain.dto.CartCreateEditDto;
import com.shakratsanzhar.domain.entity.Cart;
import com.shakratsanzhar.domain.entity.User;
import com.shakratsanzhar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartCreateEditMapper implements Mapper<CartCreateEditDto, Cart> {

    private final UserRepository userRepository;

    @Override
    public Cart map(CartCreateEditDto object) {
        Cart cart = new Cart();
        copy(object, cart);
        return cart;
    }

    @Override
    public Cart map(CartCreateEditDto fromObject, Cart toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(CartCreateEditDto object, Cart cart) {
        cart.setUserInCart(getUser(object.getUserId()));
        cart.setPrice(object.getPrice());
    }

    private User getUser(Long userId) {
        return Optional.ofNullable(userId)
                .flatMap(userRepository::findById)
                .orElse(null);
    }
}

