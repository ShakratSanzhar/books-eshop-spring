package com.shakratsanzhar.domain.dto;

import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
public class UserDetailReadDto {

    Long userId;
    String name;
    String surname;
    LocalDate birthday;
    String phone;
    LocalDateTime registrationDate;
}
