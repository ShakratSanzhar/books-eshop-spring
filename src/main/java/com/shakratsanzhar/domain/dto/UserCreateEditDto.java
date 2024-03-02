package com.shakratsanzhar.domain.dto;

import com.shakratsanzhar.domain.enums.Role;
import com.shakratsanzhar.validation.EmailInfo;
import com.shakratsanzhar.validation.UsernameInfo;
import com.shakratsanzhar.validation.group.CreateAction;
import com.shakratsanzhar.validation.group.UpdateAction;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
@Builder
public class UserCreateEditDto {

    @UsernameInfo(groups = {CreateAction.class})
    @NotEmpty(message = "Username shouldn't be empty", groups = {CreateAction.class})
    String username;

    @EmailInfo(groups = {CreateAction.class})
    @NotEmpty(message = "Email shouldn't be empty", groups = {CreateAction.class})
    String email;

    @NotEmpty(message = "Password shouldn't be empty", groups = {CreateAction.class})
    String rawPassword;

    @NotNull(message = "Role isn't selected", groups = {CreateAction.class, UpdateAction.class})
    Role role;
}
