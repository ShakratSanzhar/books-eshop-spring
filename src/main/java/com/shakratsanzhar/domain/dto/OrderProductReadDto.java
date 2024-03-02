package com.shakratsanzhar.domain.dto;

import lombok.Value;

@Value
public class OrderProductReadDto {

    Long id;
    OrderReadDto order;
    ProductReadDto product;
    Integer quantity;
}
