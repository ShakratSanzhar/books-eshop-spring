package com.shakratsanzhar.mapper;

import com.shakratsanzhar.domain.dto.UserDetailReadDto;
import com.shakratsanzhar.domain.dto.UserReadDto;
import com.shakratsanzhar.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    private final UserDetailReadMapper userDetailReadMapper;

    @Override
    public UserReadDto map(User object) {
        UserDetailReadDto userDetail = Optional.ofNullable(object.getUserDetail())
                .map(userDetailReadMapper::map)
                .orElse(null);
        return new UserReadDto(
                object.getId(),
                object.getUsername(),
                object.getEmail(),
                object.getRole(),
                userDetail
        );
    }
}
