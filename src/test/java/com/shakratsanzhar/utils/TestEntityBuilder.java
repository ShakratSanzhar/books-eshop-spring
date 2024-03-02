package com.shakratsanzhar.utils;

import com.shakratsanzhar.domain.entity.Category;
import com.shakratsanzhar.domain.entity.Product;
import com.shakratsanzhar.domain.entity.User;
import com.shakratsanzhar.domain.entity.UserDetail;
import com.shakratsanzhar.domain.enums.Role;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@UtilityClass
public final class TestEntityBuilder {

    public User createUser() {
        return User.builder()
                .username("Sanzhar")
                .email("shakrat.sg@gmail.com")
                .password("qwerty")
                .role(Role.ADMIN)
                .build();
    }

    public UserDetail createUserDetails() {
        return UserDetail.builder()
                .name("Sanzhar")
                .surname("Shakrat")
                .birthday(LocalDate.of(1999, Month.NOVEMBER,9))
                .phone("+77475005033")
                .registrationDate(LocalDateTime.now())
                .build();
    }

    public Category createMainCategory() {
        return Category.builder()
                .name("Dustum")
                .mainCategory(null)
                .build();
    }

    public Product createProduct() {
        return Product.builder()
                .name("Psycho")
                .description("book about psychology")
                .author("Bayron")
                .image("vixlop")
                .price(222)
                .remainingAmount(3)
                .createdAt(LocalDateTime.now())
                .build();
    }
}

