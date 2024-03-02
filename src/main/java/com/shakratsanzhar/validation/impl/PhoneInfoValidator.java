package com.shakratsanzhar.validation.impl;

import com.shakratsanzhar.repository.UserDetailRepository;
import com.shakratsanzhar.validation.PhoneInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneInfoValidator implements ConstraintValidator<PhoneInfo, String> {

    private final UserDetailRepository userDetailRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return userDetailRepository.findUserDetailByPhoneEquals(value).isEmpty();
    }
}
