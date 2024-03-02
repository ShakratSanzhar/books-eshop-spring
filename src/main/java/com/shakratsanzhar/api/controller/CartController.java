package com.shakratsanzhar.api.controller;

import com.shakratsanzhar.domain.dto.CartProductCreateEditDto;
import com.shakratsanzhar.domain.dto.ProductWithQuantityDto;
import com.shakratsanzhar.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/users/{userId}/carts/{cartId}/change")
    public String changeQuantity(@PathVariable("cartId") Long cartId, @PathVariable("userId") Long userId, Long productId, Integer quantity) {
        CartProductCreateEditDto cartProductCreateEditDto = new CartProductCreateEditDto();
        cartProductCreateEditDto.setCartId(cartId);
        cartProductCreateEditDto.setProductId(productId);
        cartProductCreateEditDto.setQuantity(quantity);
        cartService.changeQuantity(cartProductCreateEditDto, userId);
        return "redirect:/users/{userId}/carts/{cartId}";
    }

    @GetMapping("/users/{userId}/carts/{cartId}")
    public String find(@PathVariable("cartId") Long cartId, @PathVariable("userId") Long userId, Model model) {
        List<ProductWithQuantityDto> list = cartService.getProductsWithQuantities(cartId);
        model.addAttribute("cart", cartService.findById(cartId).get());
        model.addAttribute("list", list);
        model.addAttribute("userId", userId);
        return "cart/cart";
    }

    @PostMapping("/users/{userId}/carts/{cartId}")
    public String addProductToCart(@PathVariable("cartId") Long cartId, @PathVariable("userId") Long userId, @ModelAttribute CartProductCreateEditDto cart) {
        cart.setCartId(cartId);
        cartService.addProduct(cart, userId);
        return "redirect:/products";
    }
}

