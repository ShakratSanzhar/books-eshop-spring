package com.shakratsanzhar.domain.dto;

import lombok.Data;

@Data
public class CategoryCreateEditDto {

    String name;
    Integer parentCategoryId;
}
