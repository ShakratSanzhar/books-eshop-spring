package com.shakratsanzhar.mapper;

import com.shakratsanzhar.domain.dto.CategoryReadDto;
import com.shakratsanzhar.domain.dto.ProductReadDto;
import com.shakratsanzhar.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductReadMapper implements Mapper<Product, ProductReadDto> {

    private final CategoryReadMapper categoryReadMapper;

    @Override
    public ProductReadDto map(Product object) {
        CategoryReadDto category = Optional.ofNullable(object.getCategory())
                .map(categoryReadMapper::map)
                .orElse(null);
        return new ProductReadDto(
                object.getId(),
                category,
                object.getName(),
                object.getDescription(),
                object.getAuthor(),
                object.getImage(),
                object.getPrice(),
                object.getRemainingAmount()
        );
    }
}
