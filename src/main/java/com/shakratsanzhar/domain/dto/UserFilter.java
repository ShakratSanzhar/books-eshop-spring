package com.shakratsanzhar.domain.dto;

import com.shakratsanzhar.domain.enums.Role;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserFilter {

    String username;
    String email;
    Role role;
}
