package com.shakratsanzhar.domain.dto;

import lombok.Value;

@Value
public class CartProductReadDto {

    Long id;
    CartReadDto cart;
    ProductReadDto product;
    Integer quantity;
}
