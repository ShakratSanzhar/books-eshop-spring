package com.shakratsanzhar.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProductCreateEditDto {

    Long cartId;
    Long productId;
    Integer quantity;
}
