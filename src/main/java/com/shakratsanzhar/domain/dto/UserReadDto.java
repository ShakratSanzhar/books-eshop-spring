package com.shakratsanzhar.domain.dto;

import com.shakratsanzhar.domain.enums.Role;
import lombok.Value;

@Value
public class UserReadDto {

    Long id;
    String username;
    String email;
    Role role;
    UserDetailReadDto userDetail;
}
