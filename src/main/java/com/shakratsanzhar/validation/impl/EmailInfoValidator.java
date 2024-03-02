package com.shakratsanzhar.validation.impl;

import com.shakratsanzhar.repository.UserRepository;
import com.shakratsanzhar.validation.EmailInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailInfoValidator implements ConstraintValidator<EmailInfo, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findUserByEmailEquals(value).isEmpty();
    }
}
