package com.shakratsanzhar.validation;

import com.shakratsanzhar.validation.impl.PhoneInfoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = PhoneInfoValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = PhoneInfo.PhoneInfos.class)
public @interface PhoneInfo {

    String message() default "This phone is already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface PhoneInfos {
        PhoneInfo[] value();
    }
}
