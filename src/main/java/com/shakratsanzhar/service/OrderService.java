package com.shakratsanzhar.service;

import com.shakratsanzhar.domain.dto.OrderCreateEditDto;
import com.shakratsanzhar.domain.dto.OrderProductCreateEditDto;
import com.shakratsanzhar.domain.dto.OrderReadDto;
import com.shakratsanzhar.domain.dto.ProductQuantityError;
import com.shakratsanzhar.domain.dto.ProductWithQuantityDto;
import com.shakratsanzhar.domain.entity.Product;
import com.shakratsanzhar.domain.enums.OrderStatus;
import com.shakratsanzhar.mapper.OrderCreateEditMapper;
import com.shakratsanzhar.mapper.OrderProductCreateEditMapper;
import com.shakratsanzhar.mapper.OrderReadMapper;
import com.shakratsanzhar.mapper.ProductReadMapper;
import com.shakratsanzhar.repository.CartRepository;
import com.shakratsanzhar.repository.OrderRepository;
import com.shakratsanzhar.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderReadMapper orderReadMapper;
    private final OrderCreateEditMapper orderCreateEditMapper;
    private final OrderProductCreateEditMapper orderProductCreateEditMapper;
    private final ProductReadMapper productReadMapper;
    private final ProductRepository productRepository;
    private final CartService cartService;

    public List<OrderReadDto> findAll() {
        return orderRepository.findAll().stream()
                .map(orderReadMapper::map)
                .toList();
    }

    public Optional<OrderReadDto> findById(Long id) {
        return orderRepository.findById(id)
                .map(orderReadMapper::map);
    }

    @Transactional
    public OrderReadDto create(OrderCreateEditDto orderDto) {
        return Optional.of(orderDto)
                .map(orderCreateEditMapper::map)
                .map(orderRepository::save)
                .map(orderReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<OrderReadDto> update(Long id, OrderCreateEditDto orderDto) {
        return orderRepository.findById(id)
                .map(entity -> orderCreateEditMapper.map(orderDto, entity))
                .map(orderRepository::saveAndFlush)
                .map(orderReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return orderRepository.findById(id)
                .map(entity -> {
                    addQuantityOfProducts(getProductsWithQuantities(id));
                    orderRepository.delete(entity);
                    orderRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public void addOrder(Long userId, Long cartId) {
        Integer price = cartRepository.findById(cartId).get().getPrice();
        OrderCreateEditDto orderCreateEditDto = OrderCreateEditDto.builder()
                .userId(userId)
                .status(OrderStatus.NOT_PAID)
                .price(price)
                .createdAt(LocalDateTime.now())
                .build();
        OrderReadDto orderReadDto = create(orderCreateEditDto);
        Map<Product, Integer> productsWithQuantities = cartRepository.findAllProductsWithQuantities(cartId);
        for (Map.Entry<Product, Integer> entry : productsWithQuantities.entrySet()) {
            OrderProductCreateEditDto orderProductCreateEditDto = new OrderProductCreateEditDto(orderReadDto.getId(), entry.getKey().getId(), entry.getValue());
            orderRepository.addProductToOrder(orderProductCreateEditMapper.map(orderProductCreateEditDto));
        }
    }

    public List<OrderReadDto> getAllOrdersByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId).stream().map(orderReadMapper::map).toList();
    }

    public List<ProductWithQuantityDto> getProductsWithQuantities(Long orderId) {
        List<ProductWithQuantityDto> list = new ArrayList<>();
        Map<Product, Integer> productsWithQuantities = orderRepository.findAllProductsWithQuantities(orderId);
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
    public void confirmPayment(Long orderId) {
        orderRepository.confirmPayment(orderId, OrderStatus.PAID, LocalDateTime.now());
    }

    public boolean isProductInStock(ProductWithQuantityDto product) {
        return (product.getQuantity() <= productRepository.findQuantityOfProduct(product.getProductReadDto().getId()));
    }

    @Transactional
    public void updateQuantityOfProducts(List<ProductWithQuantityDto> products) {
        for (ProductWithQuantityDto product : products) {
            int oldQuantity = productRepository.findQuantityOfProduct(product.getProductReadDto().getId());
            int newQuantity = oldQuantity - product.getQuantity();
            productRepository.updateProductQuantity(product.getProductReadDto().getId(), newQuantity);
        }
    }

    @Transactional
    public void addQuantityOfProducts(List<ProductWithQuantityDto> products) {
        for (ProductWithQuantityDto product : products) {
            int oldQuantity = productRepository.findQuantityOfProduct(product.getProductReadDto().getId());
            int newQuantity = oldQuantity + product.getQuantity();
            productRepository.updateProductQuantity(product.getProductReadDto().getId(), newQuantity);
        }
    }

    public List<ProductQuantityError> checkForAvailability(Long cartId, List<ProductWithQuantityDto> products) {
        List<ProductQuantityError> errors = new ArrayList<>();
        for (ProductWithQuantityDto product : products) {
            if (!isProductInStock(product)) {
                var error = ProductQuantityError.builder()
                        .name(product.getProductReadDto().getName())
                        .quantity(productRepository.findQuantityOfProduct(product.getProductReadDto().getId()))
                        .build();
                errors.add(error);
            }
        }
        return errors;
    }
}
