package com.shakratsanzhar.repository;

import com.shakratsanzhar.domain.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long>, QuerydslPredicateExecutor<UserDetail> {

    Optional<UserDetail> findUserDetailByPhoneEquals(String phone);
}
