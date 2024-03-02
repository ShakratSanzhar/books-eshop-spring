package com.shakratsanzhar.mapper;

import com.shakratsanzhar.domain.dto.CategoryReadDto;
import com.shakratsanzhar.domain.entity.Category;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryReadMapper implements Mapper<Category, CategoryReadDto> {

    @Override
    public CategoryReadDto map(Category object) {
        CategoryReadDto parentCategory = Optional.ofNullable(object.getMainCategory())
                .map(this::map)
                .orElse(null);
        return new CategoryReadDto(
                object.getId(),
                object.getName(),
                parentCategory
        );
    }
}
