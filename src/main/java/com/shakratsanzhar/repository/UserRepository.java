package com.shakratsanzhar.repository;

import com.shakratsanzhar.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    User findUserByEmailAndPassword(String email, String password);

    Optional<User> findUserByEmailEquals(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findUserByUsernameEquals(String username);
}
