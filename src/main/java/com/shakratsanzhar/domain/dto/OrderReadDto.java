package com.shakratsanzhar.domain.dto;

import com.shakratsanzhar.domain.enums.OrderStatus;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class OrderReadDto {

    Long id;
    UserReadDto user;
    OrderStatus status;
    Integer price;
    LocalDateTime createdAt;
    LocalDateTime closedAt;
}
