package spring_boot_debugging.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import spring_boot_debugging.dto.CreateUserRequest;
import spring_boot_debugging.dto.UpdateUserRequest;
import spring_boot_debugging.dto.UserDTO;
import spring_boot_debugging.exception.UserAlreadyExistsException;
import spring_boot_debugging.exception.UserNotFoundException;
import spring_boot_debugging.model.User;
import spring_boot_debugging.repository.UserRepository2;

import java.util.Comparator;
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

    private UpdateUserRequest validUpdateUserRequest;
    private User updatedUser;


    Page<User> usersPage;
    User user1;
    User user2;

    @BeforeEach
    void setUp() {
        validCreateUserRequest = CreateUserRequest.builder()
                .username("David")
                .password("dav-1234")
                .email("david.henri@example.com")
                .age(31)
                .roles(List.of("USER", "ADMIN"))
                .build();

        validUpdateUserRequest = UpdateUserRequest.builder()
                .username("David")
                .password("dav-1234")
                .email("david.henri@example.com")
                .age(33)
                .roles(List.of("USER", "ADMIN"))
                .build();

        updatedUser = User.builder()
                .id(1L)
                .username("David")
                .password("dav-1234")
                .email("david.henri@example.com")
                .age(33)
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

        user1 = User.builder()
                .id(2L)
                .username("John")
                .email("john.doe@example.com")
                .password("john-1234")
                .age(27)
                .roles(List.of("USER", "ADMIN"))
                .build();

        user2 = User.builder()
                .id(3L)
                .username("Jane")
                .email("jane.doe@example.com")
                .password("jane-1234")
                .age(22)
                .roles(List.of("USER", "ADMIN"))
                .build();

        usersPage = new PageImpl<>(List.of(user1, user2));
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
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with id " + userId + " not found");
        verify(userRepository2, times(1)).findById(userId);
    }

    // [READ] Get all users --> Return page of users with pagination
    @Test
    @DisplayName("[READ] Get all users --> Return page of users with pagination")
    void should_return_page_of_users_when_getting_all_users() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        when(userRepository2.findAll(pageable)).thenReturn(usersPage);

        // Act
        Page<UserDTO> users = userService2.getAllUsers(pageable);
        List<UserDTO> userDTOList = users.getContent();

        // Assert
        assertThat(users.getNumberOfElements()).isEqualTo(2);
        assertThat(users.getTotalPages()).isEqualTo(1);

        assertThat(userDTOList).isNotEmpty();
        assertThat(userDTOList.size()).isEqualTo(usersPage.getContent().size());
        assertThat(userDTOList.get(0).getId()).isEqualTo(usersPage.getContent().get(0).getId());
        assertThat(userDTOList.get(0).getUsername()).isEqualTo(usersPage.getContent().get(0).getUsername());
        assertThat(userDTOList.get(0).getEmail()).isEqualTo(usersPage.getContent().get(0).getEmail());
        assertThat(userDTOList.get(0).getAge()).isEqualTo(usersPage.getContent().get(0).getAge());
        verify(userRepository2, times(1)).findAll(pageable);
    }

    // [READ] Get all users --> Return empty list
    @Test
    @DisplayName("[READ] Get all users --> Return empty list")
    void should_return_an_empty_list_when_getting_all_users() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        when(userRepository2.findAll(pageable)).thenReturn(new PageImpl<>(List.of(), pageable, 0));

        // Act
        Page<UserDTO> page = userService2.getAllUsers(pageable);

        // Assert
        assertThat(page.getNumberOfElements()).isZero();
        assertThat(page.getTotalPages()).isZero();
        verify(userRepository2, times(1)).findAll(pageable);
    }

    // [READ] Get all users --> Return page of users with pagination and sorting
    @Test
    @DisplayName("[READ] Get all users --> Return page of users with pagination and sorting")
    void should_return_page_of_users_with_pagination_and_sorting_when_getting_all_users() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10, Sort.by("username").ascending());
        when(userRepository2.findAll(pageable)).thenReturn(new PageImpl<>(List.of(user2, user1), pageable, 2));

        // Act
        Page<UserDTO> page = userService2.getAllUsers(pageable);

        // Assert
        assertThat(page.getNumberOfElements()).isEqualTo(2);
        assertThat(page.getTotalPages()).isEqualTo(1);

        List<UserDTO> userDTOList = page.getContent().stream()
                .toList();

        assertThat(userDTOList).isNotEmpty();
        assertThat(userDTOList.size()).isEqualTo(2);
        assertThat(userDTOList).isSortedAccordingTo(Comparator.comparing(UserDTO::getUsername));
        verify(userRepository2, times(1)).findAll(pageable);
    }

    // [UPDATE] Update user --> Retrieve user successfully
    @Test
    @DisplayName("[UPDATE] Update user --> Retrieve user successfully")
    void should_update_user_successfully_when_user_is_found() {
        // Arrange
        Long userId = 1L;
        when(userRepository2.findById(userId)).thenReturn(Optional.of(savedUser));
        when(userRepository2.save(any(User.class))).thenReturn(updatedUser);

        // Act
        UserDTO userUpdated = userService2.updateUser(userId, validUpdateUserRequest);

        // Assert
        assertThat(userUpdated).isNotNull();
        assertThat(userUpdated.getId()).isEqualTo(userId);
        assertThat(userUpdated.getUsername()).isEqualTo(validUpdateUserRequest.getUsername());
        assertThat(userUpdated.getEmail()).isEqualTo(validUpdateUserRequest.getEmail());
        assertThat(userUpdated.getAge()).isEqualTo(validUpdateUserRequest.getAge());

        verify(userRepository2, times(1)).findById(userId);
        verify(userRepository2, times(1)).save(any(User.class));
    }


    // [UPDATE] Update user --> User Not Found
    @Test
    @DisplayName("[UPDATE] Update user --> User Not Found")
    void should_throw_an_exception_if_user_not_found_when_updating_user() {
        // Arrange
        Long nonExistantId = 123L;
        when(userRepository2.findById(nonExistantId)).thenReturn(Optional.empty());

        // Act
        Throwable exception = catchThrowable(() -> userService2.updateUser(nonExistantId, validUpdateUserRequest));

        // Assert
        assertThat(exception)
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with id " + nonExistantId + " not found");

        verify(userRepository2, times(1)).findById(nonExistantId);
        verify(userRepository2, never()).save(any(User.class));
    }

    // [DELETE] Delete user --> Delete user successfully
    @Test
    @DisplayName("[DELETE] Delete user --> Delete user successfully")
    void should_delete_user_successfully_when_user_is_found() {
        // Arrange
        Long userIdToDelete = 1L;
        when(userRepository2.findById(userIdToDelete)).thenReturn(Optional.of(savedUser));

        // Act
        userService2.deleteUserById(userIdToDelete);

        // Assert
        verify(userRepository2, times(1)).findById(userIdToDelete);
        verify(userRepository2, times(1)).delete(eq(savedUser));
    }

    // [DELETE] Delete user --> User Not Found
    @Test
    @DisplayName("[DELETE] Delete user --> User Not Found")
    void should_throw_an_exception_if_user_not_found_when_deleting_user() {
        // Arrange
        Long nonExistantId = 123L;
        when(userRepository2.findById(nonExistantId)).thenReturn(Optional.empty());

        // Act
        Throwable exception = catchThrowable(() -> userService2.deleteUserById(nonExistantId));

        // Assert
        assertThat(exception)
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with id " + nonExistantId + " not found");

        verify(userRepository2, times(1)).findById(nonExistantId);
        verify(userRepository2, never()).delete(any(User.class));
    }

    // [SEARCH] Search users --> Return list of users
    @Test
    @DisplayName("[SEARCH] Search users --> Return list of users")
    void should_return_list_of_users_when_searching_users() {
        // Arrange
        String prefixToFind = "J";
        Pageable pageable = PageRequest.of(0, 10);
        when(userRepository2.findByUsernameContaining(prefixToFind, pageable)).thenReturn(new PageImpl<>(List.of(user2, user1), pageable, 2));

        // Act
        Page<UserDTO> page = userService2.searchUsers(prefixToFind, pageable);
        List<UserDTO> userDTOList = page.getContent().stream().toList();

        // Assert
        assertThat(page).isNotNull();
        assertThat(page.getNumberOfElements()).isEqualTo(2);
        assertThat(page.getTotalPages()).isEqualTo(1);

        assertThat(userDTOList).isNotEmpty();
        assertThat(userDTOList.size()).isEqualTo(2);
        assertThat(userDTOList.get(0).getUsername()).startsWith(prefixToFind);
        assertThat(userDTOList.get(1).getUsername()).startsWith(prefixToFind);
        assertThat(userDTOList.get(1).getUsername()).doesNotStartWith("david");

         verify(userRepository2, times(1)).findByUsernameContaining(prefixToFind, pageable);
    }

    // [SEARCH] Search users --> Return empty list
    @Test
    @DisplayName("[SEARCH] Search users --> Return empty list")
    void should_() {
        // Arrange
        String prefixToFind = "Y";
        Pageable pageable = PageRequest.of(0, 10);
        when(userRepository2.findByUsernameContaining(prefixToFind, pageable)).thenReturn(new PageImpl<>(List.of(), pageable, 0));

        // Act
        Page<UserDTO> page = userService2.searchUsers(prefixToFind, pageable);
        List<UserDTO> userDTOList = page.getContent().stream().toList();

        // Assert
        assertThat(page).isNotNull();
        assertThat(page.getNumberOfElements()).isZero();
        assertThat(page.getTotalPages()).isZero();
        assertThat(userDTOList).isEmpty();

        verify(userRepository2, times(1)).findByUsernameContaining(prefixToFind, pageable);
    }

}