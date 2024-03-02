package com.shakratsanzhar.mapper;

import com.shakratsanzhar.domain.dto.CategoryCreateEditDto;
import com.shakratsanzhar.domain.entity.Category;
import com.shakratsanzhar.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryCreateEditMapper implements Mapper<CategoryCreateEditDto, Category> {

    private final CategoryRepository categoryRepository;

    public Category getCategory(Integer categoryId) {
        return Optional.ofNullable(categoryId)
                .flatMap(categoryRepository::findById)
                .orElse(null);
    }

    @Override
    public Category map(CategoryCreateEditDto object) {
        Category category = new Category();
        copy(object, category);
        return category;
    }

    @Override
    public Category map(CategoryCreateEditDto fromObject, Category toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(CategoryCreateEditDto object, Category category) {
        category.setName(object.getName());
        category.setMainCategory(getCategory(object.getParentCategoryId()));
    }
}

