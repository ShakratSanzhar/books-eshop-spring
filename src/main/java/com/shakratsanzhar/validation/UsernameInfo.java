package com.shakratsanzhar.validation;

import com.shakratsanzhar.validation.impl.UsernameInfoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UsernameInfoValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameInfo {

    String message() default "This username is already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
