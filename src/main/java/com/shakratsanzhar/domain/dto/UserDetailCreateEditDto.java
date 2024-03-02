package com.shakratsanzhar.domain.dto;

import com.shakratsanzhar.validation.PhoneInfo;
import com.shakratsanzhar.validation.group.CreateAction;
import com.shakratsanzhar.validation.group.UpdateAction;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class UserDetailCreateEditDto {

    Long userId;

    @NotEmpty(message = "Name shouldn't be empty",groups = {CreateAction.class, UpdateAction.class})
    String name;

    @NotEmpty(message = "Surname shouldn't be empty",groups = {CreateAction.class,UpdateAction.class})
    String surname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Birthday isn't valid",groups = {CreateAction.class,UpdateAction.class})
    LocalDate birthday;

    @NotEmpty(message = "Phone shouldn't be empty",groups = {CreateAction.class})
    @PhoneInfo(groups = {CreateAction.class})
    String phone;

    LocalDateTime registrationDate;
}
