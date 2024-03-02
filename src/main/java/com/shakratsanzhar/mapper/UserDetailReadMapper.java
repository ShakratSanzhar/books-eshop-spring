package com.shakratsanzhar.mapper;

import com.shakratsanzhar.domain.dto.UserDetailReadDto;
import com.shakratsanzhar.domain.entity.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailReadMapper implements Mapper<UserDetail, UserDetailReadDto> {

    @Override
    public UserDetailReadDto map(UserDetail object) {
        return new UserDetailReadDto(
                object.getId(),
                object.getName(),
                object.getSurname(),
                object.getBirthday(),
                object.getPhone(),
                object.getRegistrationDate()
        );
    }
}
