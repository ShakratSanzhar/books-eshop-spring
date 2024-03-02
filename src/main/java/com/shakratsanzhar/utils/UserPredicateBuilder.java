package com.shakratsanzhar.utils;

import com.querydsl.core.types.Predicate;
import com.shakratsanzhar.domain.dto.UserFilter;

import static com.shakratsanzhar.domain.entity.QUser.user;

public class UserPredicateBuilder implements PredicateBuilder<Predicate, UserFilter> {

    @Override
    public Predicate build(UserFilter requestFilter) {
        return QPredicates.builder()
                .add(requestFilter.getUsername(), user.username::containsIgnoreCase)
                .add(requestFilter.getEmail(), user.email::containsIgnoreCase)
                .add(requestFilter.getRole(),user.role::eq)
                .buildAnd();
    }
}
