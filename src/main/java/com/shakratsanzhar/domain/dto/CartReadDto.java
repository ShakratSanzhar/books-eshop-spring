package com.shakratsanzhar.domain.dto;

import lombok.Value;

@Value
public class CartReadDto {

    Long id;
    UserReadDto user;
    Integer price;
}
