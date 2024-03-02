package com.shakratsanzhar.repository;

import com.shakratsanzhar.IntegrationTestBase;
import com.shakratsanzhar.domain.enums.Role;
import com.shakratsanzhar.utils.TestEntityBuilder;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class UserRepositoryTestIT extends IntegrationTestBase {

    private final UserRepository userRepository;

    @Test
    void shouldSaveUserWithoutUserDetails() {
        var userToSave = TestEntityBuilder.createUser();

        var savedUser = userRepository.save(userToSave);

        assertThat(savedUser).isNotNull();
    }

    @Test
    void shouldSaveUserWithUserDetails() {
        var userToSave = TestEntityBuilder.createUser();
        var userDetails = TestEntityBuilder.createUserDetails();

        var savedUser = userRepository.save(userToSave);
        userDetails.setUser(savedUser);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(userDetails.getId()).isNotNull();
    }

    @Test
    void shouldFindByIdUser() {
        var expectedUser = TestEntityBuilder.createUser();
        userRepository.save(expectedUser);

        var actualUser = userRepository.findById(expectedUser.getId());

        assertThat(actualUser).isNotNull();
        actualUser.ifPresent(user -> assertThat(user).isEqualTo(expectedUser));
    }

    @Test
    void shouldUpdateUser() {
        var userToUpdate = TestEntityBuilder.createUser();
        userRepository.save(userToUpdate);
        var user = userRepository.findById(userToUpdate.getId());
        user.get().setRole(Role.USER);
        userRepository.save(user.get());

        var actualUser = userRepository.findById(user.get().getId());

        assertThat(actualUser.get()).isEqualTo(user.get());
        assertThat(actualUser.get().getRole()).isEqualTo(Role.USER);
    }

    @Test
    void shouldDeleteUser() {
        var user = TestEntityBuilder.createUser();
        userRepository.save(user);
        var userToDelete = userRepository.findById(user.getId());

        userToDelete.ifPresent(userRepository::delete);

        assertThat(userRepository.findById(userToDelete.get().getId())).isEmpty();
    }
}
