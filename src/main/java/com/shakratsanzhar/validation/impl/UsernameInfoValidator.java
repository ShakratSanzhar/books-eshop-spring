package com.shakratsanzhar.validation.impl;

import com.shakratsanzhar.repository.UserRepository;
import com.shakratsanzhar.validation.UsernameInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsernameInfoValidator implements ConstraintValidator<UsernameInfo,String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findUserByUsernameEquals(value).isEmpty();
    }
}
