package spring_boot_debugging.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import spring_boot_debugging.dto.CreateUserRequest;
import spring_boot_debugging.dto.UpdateUserRequest;
import spring_boot_debugging.dto.UserDTO;
import spring_boot_debugging.security.SecurityConfig;
import spring_boot_debugging.service.UserService2;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserControllerV2.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc(addFilters = true)
class UserControllerV2Test {

    public static final String BASE_URL = "/v1/api/users";

    private UserDTO user1;
    private UserDTO user2;
    private CreateUserRequest validUserCreationRequest;
    private CreateUserRequest invalidUserCreationRequest;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService2 userService2;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        user1 = UserDTO.builder()
                .id(1L)
                .username("John")
                .email("john.doe@example.com")
                .age(27)
                .build();

        user2 = UserDTO.builder()
                .id(2L)
                .username("Jane")
                .email("jane.doe@example.com")
                .age(22)
                .build();

        validUserCreationRequest = CreateUserRequest.builder()
                .username("David")
                .password("david-1234")
                .email("david.doe@example.com")
                .age(27)
                .roles(List.of("USER", "ADMIN"))
                .build();

        invalidUserCreationRequest = CreateUserRequest.builder()
                .username("")
                .password("david-1234")
                .email("david.doe@example.com")
                .age(27)
                .roles(List.of("USER"))
                .build();

    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("[POST] Create User --> 201 OK")
    void should_create_user_successfully_when_valid_user_is_provided() throws Exception {
        when(userService2.createUser(any(CreateUserRequest.class))).thenReturn(user1);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserCreationRequest)))
                .andExpect(status().isCreated());

        verify(userService2, times(1)).createUser(any(CreateUserRequest.class));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("[POST] Create User --> 400 Bad Request")
    void should_return_400_bad_request_when_invalid_user_is_provided() throws Exception {
        // Act & Assert
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUserCreationRequest))
        ).andExpect(status().isBadRequest());

        verify(userService2, never()).createUser(any(CreateUserRequest.class));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("[POST] Create User --> 500")
    void should_return_500_when_exception_is_thrown() throws Exception{
        // Arrange
        when(userService2.createUser(any(CreateUserRequest.class)))
                .thenThrow(new RuntimeException("Unexpected error"));

        // Act
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserCreationRequest)))
                .andExpect(status().isInternalServerError());

        // Assert
        verify(userService2, times(1)).createUser(any(CreateUserRequest.class));

    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("[GET] Get User --> 200 OK")
    void should_return_user_with_status_200_ok_when_user_exists() throws Exception{
        // Arrange
        Long userId = 1L;
        when(userService2.getUserById(userId)).thenReturn(user1);

        // Act
        mockMvc.perform(get(BASE_URL + "/" + userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.username").value(user1.getUsername()))
                .andExpect(jsonPath("$.email").value(user1.getEmail()))
                .andExpect(jsonPath("$.age").value(user1.getAge()))
                .andExpect(status().isOk());

        // Assert
        verify(userService2, times(1)).getUserById(userId);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("[GET] Get User --> 404 Not Found")
    void should_return_status_404_not_found_when_user_does_not_exist() throws Exception{
        // Arrange
        Long nonExistingUserId = 100L;
        doThrow(new UsernameNotFoundException("User with id " + nonExistingUserId + " not found"))
                .when(userService2).getUserById(nonExistingUserId);

        // Act
        mockMvc.perform(get(BASE_URL + "/" + nonExistingUserId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // Assert
        verify(userService2, times(1)).getUserById(nonExistingUserId);

    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("[GET] Get User --> 400 Bad Request")
    void should_return_400_bad_request_when_id_is_invalid() throws Exception {
        // Arrange
        Long userId = -1L;

        // Act
        mockMvc.perform(get(BASE_URL + "/" + userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // Assert
        verify(userService2, never()).getUserById(userId);
    }


    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("[GET] Get All Users --> 200 OK")
    void should_return_all_users_with_status_200_ok() throws Exception{
        // Arrange
        Page<UserDTO> page = new PageImpl<>(List.of(user1, user2));
        when(userService2.getAllUsers(any(Pageable.class))).thenReturn(page);

        // Act
        mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "10")
                )
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content.[0].id").value(user1.getId()))
                .andExpect(jsonPath("$.content.[0].username").value(user1.getUsername()))
                .andExpect(jsonPath("$.content.[0].email").value(user1.getEmail()))
                .andExpect(jsonPath("$.content.[0].age").value(user1.getAge()))
                .andExpect(jsonPath("$.content.[1].id").value(user2.getId()))
                .andExpect(jsonPath("$.content.[1].username").value(user2.getUsername()))
                .andExpect(jsonPath("$.content.[1].email").value(user2.getEmail()))
                .andExpect(jsonPath("$.content.[1].age").value(user2.getAge()))
                .andExpect(status().isOk());

        // Assert
        verify(userService2, times(1)).getAllUsers(any(Pageable.class));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("[PUT] Update User --> 200 OK")
    void should_update_user_and_return_status_200_ok() throws Exception {
        // Arrange
        Long userId = 1L;
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .username("david")
                .email("david.doe@example.com")
                .age(30)
                .build();

        UserDTO updatedUserResponse = UserDTO.builder()
                .id(userId)
                .username("david")
                .email("david.doe@example.com")
                .age(30)
                .build();

        when(userService2.updateUser(userId, updateUserRequest)).thenReturn(updatedUserResponse);

        // Act
        mockMvc.perform(put(BASE_URL + "/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserRequest))
                )
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.username").value("david"))
                .andExpect(jsonPath("$.email").value("david.doe@example.com"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(status().isOk());

        // Assert
        verify(userService2, times(1)).updateUser(userId, updateUserRequest);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("[PUT] Update User --> 404 Not Found")
    void should_return_404_not_found_when_user_does_not_exist() throws Exception {
        // Arrange
        Long nonExistingUserId = 100L;
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .username("david")
                .email("david.doe@example.com")
                .age(30)
                .build();

        when(userService2.updateUser(nonExistingUserId, updateUserRequest))
                .thenThrow(new UsernameNotFoundException("User with id " + nonExistingUserId + " not found"));

        // Act
        mockMvc.perform(put(BASE_URL + "/" + nonExistingUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserRequest))
                )
                .andExpect(jsonPath("$.title").value("User Not Found"))
                .andExpect(status().isNotFound());

        // Assert
        verify(userService2, times(1)).updateUser(nonExistingUserId, updateUserRequest);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("[PUT] Update User --> 400 Bad Request")
    void should_return_400_bad_request_when_update_with_invalid_id() throws Exception {
        // Arrange
        Long invalidUserId = -1L;
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .username("david")
                .email("david.doe@example.com")
                .age(30)
                .build();

        // Act
        mockMvc.perform(put(BASE_URL + "/" + invalidUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserRequest)))
                .andExpect(status().isBadRequest());

        // Assert
        verify(userService2, never()).updateUser(invalidUserId, updateUserRequest);
    }


    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("[DELETE] Delete User --> 204 No Content")
    void should_delete_user_and_return_status_204_no_content() throws Exception {
        // Arrange
        Long userId = 1L;

        // Act
        mockMvc.perform(delete(BASE_URL + "/" + userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Assert
        verify(userService2, times(1)).deleteUserById(userId);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("[DELETE] Delete User --> 404 Not Found")
    void should_return_status_400_not_found_when_user_is_not_found() throws Exception {
        // Arrange
        Long userId = 24L;
        doThrow(new UsernameNotFoundException("User with id " + userId + " not found"))
                .when(userService2).deleteUserById(userId);

        // Act
        mockMvc.perform(delete(BASE_URL + "/" + userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // Assert
        verify(userService2, times(1)).deleteUserById(userId);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("[DELETE] Delete User --> 400 Bad Request")
    void should_return_400_bad_request_when_id_invalid() throws Exception {
        // Arrange
        Long invalidUserId = -1L;

        // Act
        mockMvc.perform(delete(BASE_URL + "/" + invalidUserId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // Assert
        verify(userService2, never()).deleteUserById(invalidUserId);
    }

    // [GET] Search User --> 200 OK
    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("[GET] Search User --> 200 OK")
    void should_reuturn_searched_user_and_status_200_ok_when_user_is_authenticated() throws Exception {
        // Arrange
        String username = "John";
        Pageable pageable = PageRequest.of(
                0,
                10,
                Sort.by("username").ascending());

        Page<UserDTO> page = new PageImpl<>(List.of(user1), pageable, 1);

        when(userService2.searchUser(eq(username), any(Pageable.class))).thenReturn(page);

        // Act
        mockMvc.perform(get(BASE_URL + "/search")
                .contentType(MediaType.APPLICATION_JSON)
                        .param("username", username)
                        .param("page", "0")
                        .param("size", "10")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].username").value(username))
                .andExpect(jsonPath("$.totalElements").value(1));

        // Assert
        verify(userService2, times(1)).searchUser(eq(username), any(Pageable.class));
    }

    // [GET] Search User --> 401 Unauthorized
    @Test
    @DisplayName("[GET] Search User --> 401 Unauthorized")
    void should_return_401_unauthorized_when_searching_user_and_not_authenticated() throws Exception {
        // Arrange


        // Act
        mockMvc.perform(get(BASE_URL + "/search")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        // Assert
        verify(userService2, never()).searchUser(any(String.class), any(Pageable.class));
    }

    // [GET] Search User --> 400 Bad Request
    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("[GET] Search User --> 400 Bad Request")
    void should_return_400_bad_for_search_user_when_username_is_invalid() throws Exception {
        // Arrange
        String invalidUsername = "";
        Pageable pageable = PageRequest.of(
                0,
                10,
                Sort.by("username").ascending());

        Page<UserDTO> page = new PageImpl<>(List.of(user1), pageable, 1);

        when(userService2.searchUser(eq(invalidUsername), any(Pageable.class))).thenReturn(page);

        // Act
        mockMvc.perform(get(BASE_URL + "/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username", invalidUsername)
                        .param("page", "0")
                        .param("size", "10")
                )
                .andExpect(status().isBadRequest());

        // Assert
        verify(userService2, never()).searchUser(any(String.class), any(Pageable.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("[GET] Users Statistics --> 200 OK")
    void should_return_users_statistics_and_status_200_ok_when_user_is_admin() throws Exception {
        // Arrange
        when(userService2.countAllUsers()).thenReturn(2L);
        when(userService2.countAdultUsers()).thenReturn(2);

        // Act & Assert
        mockMvc.perform(get(BASE_URL + "/stats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUsers").value(2))
                .andExpect(jsonPath("$.adultUsers").value(2))
                .andExpect(jsonPath("$.adultUsersPercentage").isNumber());

        // Assert
        verify(userService2, times(1)).countAllUsers();
        verify(userService2, times(1)).countAdultUsers();
    }

    @Test
    @DisplayName("[GET] Users Statistics --> 401 Unauthorized")
    void should_return_status_401_unauthorized_when_user_is_not_authenticated() throws Exception {
        // Act & Assert
        mockMvc.perform(get(BASE_URL + "/stats")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(userService2, never()).getAllUsers(any(Pageable.class));
        verify(userService2, never()).countAdultUsers();
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("[GET] Users Statistics --> 403 Forbidden")
    void should_return_status_403_forbidden_when_user_is_not_admin() throws Exception {
        // Act & Assert
        mockMvc.perform(get(BASE_URL + "/stats")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());

        verify(userService2, never()).getAllUsers(any(Pageable.class));
        verify(userService2, never()).countAdultUsers();
    }

}