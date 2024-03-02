package com.shakratsanzhar.validation;

import com.shakratsanzhar.validation.impl.ProductNameInfoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ProductNameInfoValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductNameInfo {

    String message() default "This name is already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
