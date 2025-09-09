package spring_boot_debugging.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import spring_boot_debugging.dto.CreateUserRequest;
import spring_boot_debugging.dto.UserDTO;
import spring_boot_debugging.model.User;
import spring_boot_debugging.service.UserService2;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserControllerV2.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerV2Test {

    public static final String BASE_URL = "/v1/api/users";

    private UserDTO user1;
    private UserDTO user2;
    private CreateUserRequest createUserRequest;
    private UserControllerV2 userController;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService2 userService2;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        //userController = new UserControllerV2(userService2);

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

        createUserRequest = CreateUserRequest.builder()
                .username("David")
                .password("david-1234")
                .email("david.doe@example.com")
                .age(27)
                .roles(List.of("USER"))
                .build();

    }

    @Test
    @DisplayName("[POST] Create User --> 201 OK")
    void should_create_user_successfully_when_valid_user_is_provided() throws Exception {
        when(userService2.createUser(any(CreateUserRequest.class))).thenReturn(user1);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isCreated());

        verify(userService2, times(1)).createUser(any(CreateUserRequest.class));
    }

    @Test
    @DisplayName("[POST] Create User --> 400 Bad Request")
    void should_return_400_bad_request_when_invalid_user_is_provided() throws Exception {
        // Arrange
        CreateUserRequest invalidUser = CreateUserRequest.builder()
                .username("")
                .password("david-1234")
                .email("david.doe@example.com")
                .age(27)
                .roles(List.of("USER"))
                .build();

        // Act
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser))
        ).andExpect(status().isBadRequest());

        // Assert
        verify(userService2, never()).createUser(any(CreateUserRequest.class));
    }


    // [POST] Create User --> 500

    // [GET] Get User --> 200 OK
    // [GET] Get User --> 404 Not Found

    // [GET] Get All Users --> 200 OK
    // [GET] Get All Users --> 404 Not Found

    // [PUT] Update User --> 200 OK
    // [PUT] Update User --> 404 Not Found

    // [DELETE] Delete User --> 200 OK
    // [DELETE] Delete User --> 400 Bad Request
    // [DELETE] Delete User --> 404 Not Found


}