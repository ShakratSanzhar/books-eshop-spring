package com.shakratsanzhar.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductQuantityError {

    String name;
    Integer quantity;
}
