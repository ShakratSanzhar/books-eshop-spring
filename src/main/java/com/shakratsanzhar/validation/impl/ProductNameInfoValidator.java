package com.shakratsanzhar.validation.impl;

import com.shakratsanzhar.repository.ProductRepository;
import com.shakratsanzhar.validation.ProductNameInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductNameInfoValidator implements ConstraintValidator<ProductNameInfo, String> {

    private final ProductRepository productRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return productRepository.findProductByNameEquals(value).isEmpty();
    }
}
