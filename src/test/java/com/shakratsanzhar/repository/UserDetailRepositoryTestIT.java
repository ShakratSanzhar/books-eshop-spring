package com.shakratsanzhar.repository;

import com.shakratsanzhar.IntegrationTestBase;
import com.shakratsanzhar.utils.TestEntityBuilder;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.orm.jpa.JpaSystemException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@RequiredArgsConstructor
public class UserDetailRepositoryTestIT extends IntegrationTestBase {

    private final UserDetailRepository userDetailRepository;
    private final UserRepository userRepository;

    @Test
    void shouldSaveUserDetailOfExistingUser() {
        var user = TestEntityBuilder.createUser();
        var userDetail = TestEntityBuilder.createUserDetails();
        userRepository.save(user);
        userDetail.setUser(user);

        var actualUserDetail = userDetailRepository.save(userDetail);

        assertThat(actualUserDetail).isNotNull();
        assertThat(actualUserDetail).isEqualTo(userDetail);
    }

    @Test
    void shouldThrowExceptionIfUserNotExist() {
        var user = TestEntityBuilder.createUser();
        var userDetail = TestEntityBuilder.createUserDetails();
        userDetail.setUser(user);

        assertThrows(JpaSystemException.class, () -> userDetailRepository.save(userDetail));
    }

    @Test
    void shouldUpdateUserDetail() {
        var user = TestEntityBuilder.createUser();
        var userDetail = TestEntityBuilder.createUserDetails();
        userRepository.save(user);
        userDetail.setUser(user);
        userDetailRepository.save(userDetail);
        var expectedUserDetail = userDetailRepository.findById(user.getId());
        expectedUserDetail.get().setName("Sukhrap");
        userDetailRepository.save(expectedUserDetail.get());

        var actualUserDetail = userDetailRepository.findById(user.getId());

        assertThat(actualUserDetail).isNotEmpty();
        actualUserDetail.ifPresent(u -> assertThat(u.getName()).isEqualTo(expectedUserDetail.get().getName()));
    }

    @Test
    void findByIdUserDetail() {
        var user = TestEntityBuilder.createUser();
        userRepository.save(user);
        var userDetail = TestEntityBuilder.createUserDetails();
        userDetail.setUser(user);
        userDetailRepository.save(userDetail);

        var actualUserDetail = userDetailRepository.findById(user.getId());

        assertThat(actualUserDetail).isNotEmpty();
        actualUserDetail.ifPresent(userDetail1 -> assertThat(userDetail1).isEqualTo(userDetail));
    }

    @Test
    void shouldDeleteUserDetail() {
        var user = TestEntityBuilder.createUser();
        userRepository.save(user);
        var userDetail = TestEntityBuilder.createUserDetails();
        userDetail.setUser(user);
        userDetailRepository.save(userDetail);

        userDetailRepository.deleteById(user.getId());
        var actualUserDetail = userDetailRepository.findById(user.getId());
        var actualUser = userRepository.findById(user.getId());

        assertThat(actualUserDetail).isEmpty();
        assertThat(actualUser).isEmpty();
    }
}

