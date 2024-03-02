package com.shakratsanzhar.service;

import com.shakratsanzhar.domain.dto.CartCreateEditDto;
import com.shakratsanzhar.domain.dto.CartProductCreateEditDto;
import com.shakratsanzhar.domain.dto.CartReadDto;
import com.shakratsanzhar.domain.dto.ProductWithQuantityDto;
import com.shakratsanzhar.domain.entity.CartProduct;
import com.shakratsanzhar.domain.entity.Product;
import com.shakratsanzhar.domain.entity.User;
import com.shakratsanzhar.mapper.CartCreateEditMapper;
import com.shakratsanzhar.mapper.CartProductCreateEditMapper;
import com.shakratsanzhar.mapper.CartReadMapper;
import com.shakratsanzhar.mapper.ProductReadMapper;
import com.shakratsanzhar.repository.CartRepository;
import com.shakratsanzhar.repository.ProductRepository;
import com.shakratsanzhar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final CartReadMapper cartReadMapper;
    private final CartCreateEditMapper cartCreateEditMapper;
    private final CartProductCreateEditMapper cartProductCreateEditMapper;
    private final ProductReadMapper productReadMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Optional<CartReadDto> findById(Long id) {
        return cartRepository.findById(id)
                .map(cartReadMapper::map);
    }

    @Transactional
    public CartReadDto create(CartCreateEditDto cartDto) {
        return Optional.of(cartDto)
                .map(cartCreateEditMapper::map)
                .map(cartRepository::save)
                .map(cartReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<CartReadDto> update(Long id, CartCreateEditDto cartDto) {
        return cartRepository.findById(id)
                .map(entity -> cartCreateEditMapper.map(cartDto, entity))
                .map(cartRepository::saveAndFlush)
                .map(cartReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return cartRepository.findById(id)
                .map(entity -> {
                    cartRepository.delete(entity);
                    cartRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    public Optional<CartReadDto> findCartByUser(Long userId) {
        User user = userRepository.findById(userId).get();
        return cartRepository.findCartByUserInCart(user)
                .map(cartReadMapper::map);
    }

    public List<ProductWithQuantityDto> getProductsWithQuantities(Long cartId) {
        List<ProductWithQuantityDto> list = new ArrayList<>();
        Map<Product, Integer> productsWithQuantities = cartRepository.findAllProductsWithQuantities(cartId);
        for (Map.Entry<Product, Integer> entry : productsWithQuantities.entrySet()) {
            ProductWithQuantityDto productWithQuantityDto = ProductWithQuantityDto.builder()
                    .productReadDto(productReadMapper.map(entry.getKey()))
                    .quantity(entry.getValue())
                    .build();
            list.add(productWithQuantityDto);
        }
        return list;
    }

    @Transactional
    public void addProduct(CartProductCreateEditDto cartProductCreateEditDto, Long userId) {
        Optional<CartProduct> cartProduct = cartRepository.findCartProductByCartIdAndProductId(cartProductCreateEditDto.getCartId(), cartProductCreateEditDto.getProductId());
        if (cartProduct.isEmpty()) {
            cartRepository.addProductToCart(cartProductCreateEditMapper.map(cartProductCreateEditDto));
        } else {
            cartRepository.updateCartProductQuantity(cartProduct.get().getId(), cartProduct.get().getQuantity() + cartProductCreateEditDto.getQuantity());
        }
        Integer oldPrice = findById(cartProductCreateEditDto.getCartId()).get().getPrice();
        Integer newPrice = (cartProductCreateEditDto.getQuantity()) * (productRepository.findById(cartProductCreateEditDto.getProductId()).get().getPrice()) + oldPrice;
        CartCreateEditDto cartCreateEditDto = new CartCreateEditDto(userId, newPrice);
        update(cartProductCreateEditDto.getCartId(), cartCreateEditDto);
    }

    @Transactional
    public void changeQuantity(CartProductCreateEditDto cartProductCreateEditDto, Long userId) {
        Integer productPrice = productRepository.findById(cartProductCreateEditDto.getProductId()).get().getPrice();
        Optional<CartProduct> cartProduct = cartRepository.findCartProductByCartIdAndProductId(cartProductCreateEditDto.getCartId(), cartProductCreateEditDto.getProductId());
        Integer oldQuantity = cartProduct.get().getQuantity();
        Integer newQuantity = cartProductCreateEditDto.getQuantity();
        Integer oldPriceWithOldQuantity = findById(cartProductCreateEditDto.getCartId()).get().getPrice();
        Integer newPrice = oldPriceWithOldQuantity - (oldQuantity * productPrice) + (newQuantity * productPrice);
        cartRepository.updateCartProductQuantity(cartProduct.get().getId(), newQuantity);
        CartCreateEditDto cartCreateEditDto = new CartCreateEditDto(userId, newPrice);
        update(cartProductCreateEditDto.getCartId(), cartCreateEditDto);
    }
}

