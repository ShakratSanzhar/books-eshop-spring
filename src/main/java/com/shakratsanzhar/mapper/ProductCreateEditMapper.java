package com.shakratsanzhar.mapper;

import com.shakratsanzhar.domain.dto.ProductCreateEditDto;
import com.shakratsanzhar.domain.entity.Category;
import com.shakratsanzhar.domain.entity.Product;
import com.shakratsanzhar.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductCreateEditMapper implements Mapper<ProductCreateEditDto, Product> {

    private final CategoryRepository categoryRepository;

    @Override
    public Product map(ProductCreateEditDto object) {
        Product product = new Product();
        copy(object, product);
        return product;
    }

    @Override
    public Product map(ProductCreateEditDto fromObject, Product toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(ProductCreateEditDto object, Product product) {
        product.setCategory(getCategory(object.getCategoryId()));
        product.setName(object.getName());
        product.setDescription(object.getDescription());
        product.setAuthor(object.getAuthor());
        if (object.getImage() != null) {
            product.setImage(object.getImage().getOriginalFilename());
        }
        product.setPrice(object.getPrice());
        product.setRemainingAmount(object.getRemainingAmount());
        if (object.getCreatedAt() != null) {
            product.setCreatedAt(object.getCreatedAt());
        }
    }

    private Category getCategory(Integer categoryId) {
        return Optional.ofNullable(categoryId)
                .flatMap(categoryRepository::findById)
                .orElse(null);
    }
}
