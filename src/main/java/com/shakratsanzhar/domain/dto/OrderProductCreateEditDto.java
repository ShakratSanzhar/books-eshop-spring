package com.shakratsanzhar.domain.dto;

import lombok.Value;

@Value
public class OrderProductCreateEditDto {

    Long orderId;
    Long productId;
    Integer quantity;
}
