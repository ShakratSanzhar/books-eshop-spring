package com.shakratsanzhar.utils;

import com.querydsl.core.types.Predicate;
import com.shakratsanzhar.domain.dto.ProductFilter;
import org.springframework.stereotype.Component;

import static com.shakratsanzhar.domain.entity.QProduct.product;

@Component
public class ProductPredicateBuilder implements PredicateBuilder<Predicate, ProductFilter> {

    @Override
    public Predicate build(ProductFilter requestFilter) {
        return QPredicates.builder()
                .add(requestFilter.getMainCategoryName(), product.category.mainCategory.name::eq)
                .add(requestFilter.getSubCategoryName(), product.category.name::eq)
                .add(requestFilter.getAuthor(),product.author::eq)
                .add(requestFilter.getPrice(),product.price::loe)
                .add(requestFilter.getRemainingAmount(),product.remainingAmount::loe)
                .buildAnd();
    }
}
