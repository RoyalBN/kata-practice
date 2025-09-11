package spring_boot_debugging.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;
import spring_boot_debugging.dto.CreateUserRequest;
import spring_boot_debugging.dto.UserDTO;
import spring_boot_debugging.exception.UserAlreadyExistsException;
import spring_boot_debugging.model.User;
import spring_boot_debugging.repository.UserRepository2;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserService2Test {

    @Mock
    private UserRepository2 userRepository2;

    @InjectMocks
    private UserService2 userService2;

    private User savedUser;
    private CreateUserRequest validCreateUserRequest;
    private CreateUserRequest invalidCreateUserRequest;

    @BeforeEach
    void setUp() {
        validCreateUserRequest = CreateUserRequest.builder()
                .username("David")
                .password("dav-1234")
                .email("david.henri@example.com")
                .age(31)
                .roles(List.of("USER", "ADMIN"))
                .build();

        invalidCreateUserRequest = CreateUserRequest.builder()
                .username("")
                .password("pasds-1234")
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

    // [CREATE] Create user successfully
    @Test
    @DisplayName("[CREATE] Create user --> Successfully created")
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
        verify(userRepository2, times(1)).save(any(User.class));
    }

    // [CREATE] Create user --> User already exists
    @Test
    @DisplayName("[CREATE] Create user --> User already exists")
    void should_throw_an_error_if_user_already_exists_when_creating_user() {
        // Arrange
        when(userRepository2.findByEmail(validCreateUserRequest.getEmail())).thenReturn(Optional.of(savedUser));

        // Act
        Throwable exception = catchThrowable(() -> userService2.createUser(validCreateUserRequest));

        // Assert
        assertThat(exception)
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("User with email " + validCreateUserRequest.getEmail() + " already exists");
        verify(userRepository2, never()).save(any(User.class));
    }

    // [READ] Get user by id --> Successfully retrieved
    @Test
    @DisplayName("[READ] Get user by id --> Successfully retrieved")
    void should_retrieve_user_successfully_when_id_is_valid() {
        // Arrange
        Long userId = 1L;
        when(userRepository2.findById(userId)).thenReturn(Optional.of(savedUser));

        // Act
        UserDTO retrievedUser = userService2.getUserById(userId);

        // Assert
        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getId()).isEqualTo(userId);
        assertThat(retrievedUser.getUsername()).isEqualTo(savedUser.getUsername());
        assertThat(retrievedUser.getEmail()).isEqualTo(savedUser.getEmail());
        assertThat(retrievedUser.getAge()).isEqualTo(savedUser.getAge());
        verify(userRepository2, times(1)).findById(userId);
    }


    // [READ] Get user by id --> User not found
    @Test
    @DisplayName("[READ] Get user by id --> User not found")
    void should_throw_an_exception_if_user_not_found_when_retrieving_user() {
        // Arrange
        Long userId = 123L;
        when(userRepository2.findById(userId)).thenReturn(Optional.empty());

        // Act
        Throwable exception = catchThrowable(() -> userService2.getUserById(userId));

        // Assert
        assertThat(exception)
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User with id " + userId + " not found");
        verify(userRepository2, times(1)).findById(userId);
    }

    // [READ] Get all users --> Return list of users
    // [READ] Get all users --> Return empty list
    // [READ] Get all users --> Return page of users
    // [READ] Get all users --> Return page of users with pagination
    // [READ] Get all users --> Return page of users with pagination and sorting

    // [UPDATE] Update user --> 200 OK
    // [UPDATE] Update user --> 404 Not Found
    // [UPDATE] Update user --> 400 Bad Request

    // [DELETE] Delete user --> 200 OK
    // [DELETE] Delete user --> 404 Not Found
    // [DELETE] Delete user --> 400 Bad Request


}