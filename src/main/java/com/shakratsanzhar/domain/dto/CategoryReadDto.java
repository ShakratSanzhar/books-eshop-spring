package com.shakratsanzhar.domain.dto;

import lombok.Value;

@Value
public class CategoryReadDto {

    Integer id;
    String name;
    CategoryReadDto parentCategory;
}
