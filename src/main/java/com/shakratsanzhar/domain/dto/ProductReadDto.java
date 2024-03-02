package com.shakratsanzhar.domain.dto;

import lombok.Value;

@Value
public class ProductReadDto {

    Long id;
    CategoryReadDto category;
    String name;
    String description;
    String author;
    String image;
    Integer price;
    Integer remainingAmount;
}
