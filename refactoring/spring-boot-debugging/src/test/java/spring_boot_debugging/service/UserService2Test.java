package spring_boot_debugging.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring_boot_debugging.dto.CreateUserRequest;
import spring_boot_debugging.dto.UserDTO;
import spring_boot_debugging.model.User;
import spring_boot_debugging.repository.UserRepository2;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserService2Test {

    @Mock
    private UserRepository2 userRepository2;

    @InjectMocks
    private UserService2 userService2;

    private User user;
    private User savedUser;

    private CreateUserRequest validCreateUserRequest;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        validCreateUserRequest = CreateUserRequest.builder()
                .username("David")
                .password("dav-1234")
                .email("david.henri@example.com")
                .age(31)
                .roles(List.of("USER", "ADMIN"))
                .build();

        savedUser = User.builder()
                .id(1L)
                .username("David")
                .email("david.henri@example.com")
                .password("dav-1234")
                .age(31)
                .roles(List.of("USER", "ADMIN"))
                .build();

    }

    // [CREATE] Create user --> 201 Created
    @Test
    @DisplayName("[CREATE] Create user --> 201 Created")
    void should_create_user_successfully_when_request_is_valid() {
        // Arrange
        when(userRepository2.save(any(User.class))).thenReturn(savedUser);

        // Act
        UserDTO newUser = userService2.createUser(validCreateUserRequest);

        // Assert
        assertThat(newUser).isNotNull();
        assertThat(newUser.getId()).isEqualTo(1L);
        assertThat(newUser.getUsername()).isEqualTo(validCreateUserRequest.getUsername());
        assertThat(newUser.getEmail()).isEqualTo(validCreateUserRequest.getEmail());
        assertThat(newUser.getAge()).isEqualTo(validCreateUserRequest.getAge());
    }

    // [CREATE] Create user --> 400 Bad Request


    // [CREATE] Create user --> 400 User already exists

    // [READ] Get user by id --> 200 OK
    // [READ] Get user by id --> 404 Not Found
    // [READ] Get user by id --> 400 Bad Request

    // [READ] Get all users --> 200 OK
    // [READ] Get all users --> 404 Not Found
    // [READ] Get all users --> 400 Bad Request

    // [UPDATE] Update user --> 200 OK
    // [UPDATE] Update user --> 404 Not Found
    // [UPDATE] Update user --> 400 Bad Request

    // [DELETE] Delete user --> 200 OK
    // [DELETE] Delete user --> 404 Not Found
    // [DELETE] Delete user --> 400 Bad Request


}