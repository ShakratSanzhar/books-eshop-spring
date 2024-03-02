package com.shakratsanzhar.domain.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductFilter {

    String mainCategoryName;
    String subCategoryName;
    String author;
    Integer price;
    Integer remainingAmount;
}
