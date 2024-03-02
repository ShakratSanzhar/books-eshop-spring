package com.shakratsanzhar.mapper;

import com.shakratsanzhar.domain.dto.UserDetailCreateEditDto;
import com.shakratsanzhar.domain.entity.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailCreateEditMapper implements Mapper<UserDetailCreateEditDto, UserDetail> {

    @Override
    public UserDetail map(UserDetailCreateEditDto object) {
        UserDetail userDetail = new UserDetail();
        copy(object, userDetail);
        return userDetail;
    }

    @Override
    public UserDetail map(UserDetailCreateEditDto fromObject, UserDetail toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(UserDetailCreateEditDto object, UserDetail userDetail) {
        if (object.getUserId() != null) {
            userDetail.setId(object.getUserId());
        }
        userDetail.setName(object.getName());
        userDetail.setSurname(object.getSurname());
        userDetail.setBirthday(object.getBirthday());
        userDetail.setPhone(object.getPhone());
        if (object.getRegistrationDate() != null) {
            userDetail.setRegistrationDate(object.getRegistrationDate());
        }
    }
}
