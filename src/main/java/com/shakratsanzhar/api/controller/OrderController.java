package com.shakratsanzhar.api.controller;

import com.shakratsanzhar.domain.dto.ProductQuantityError;
import com.shakratsanzhar.domain.dto.ProductWithQuantityDto;
import com.shakratsanzhar.service.CartService;
import com.shakratsanzhar.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    @org.springframework.web.bind.annotation.GetMapping("/users/{userId}/orders")
    public String findOrdersOfUser(@org.springframework.web.bind.annotation.PathVariable("userId") Long userId, org.springframework.ui.Model model) {
        model.addAttribute("orders", orderService.getAllOrdersByUserId(userId));
        return "order/ordersOfUser";
    }

    @org.springframework.web.bind.annotation.PostMapping("/users/{userId}/orders")
    public String addOrder(@org.springframework.web.bind.annotation.PathVariable("userId") Long userId, @org.springframework.web.bind.annotation.SessionAttribute("cartId") Long cartId, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        java.util.List<ProductWithQuantityDto> products = cartService.getProductsWithQuantities(cartId);
        java.util.List<ProductQuantityError> errors = orderService.checkForAvailability(cartId, products);
        if (!errors.isEmpty()) {
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/users/{userId}/carts/" + cartId;
        }
        orderService.addOrder(userId, cartId);
        orderService.updateQuantityOfProducts(products);
        return "redirect:/users/{userId}/orders";
    }

    @org.springframework.web.bind.annotation.GetMapping("/users/{userId}/orders/{orderId}")
    public String findOrdersByUserId(@org.springframework.web.bind.annotation.PathVariable("orderId") Long orderId, @org.springframework.web.bind.annotation.PathVariable("userId") Long userId, org.springframework.ui.Model model) {
        model.addAttribute("list", orderService.getProductsWithQuantities(orderId));
        return "order/order";
    }

    @org.springframework.web.bind.annotation.PostMapping("/orders/{id}/confirm")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public String update(@org.springframework.web.bind.annotation.PathVariable("id") Long orderId) {
        orderService.confirmPayment(orderId);
        return "redirect:/orders";
    }

    @org.springframework.web.bind.annotation.PostMapping("/users/{userId}/orders/{orderId}/delete")
    public String delete(@org.springframework.web.bind.annotation.PathVariable("orderId") Long orderId, @org.springframework.web.bind.annotation.PathVariable("userId") Long userId) {
        if (!orderService.delete(orderId)) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND);
        }
        return "redirect:/users/{userId}/orders";
    }

    @org.springframework.web.bind.annotation.GetMapping("/orders")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public String findAllOrders(org.springframework.ui.Model model) {
        model.addAttribute("list", orderService.findAll());
        return "order/orders";
    }
}

