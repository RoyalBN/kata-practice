package spring_boot_debugging.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import spring_boot_debugging.dto.CreateUserRequest;
import spring_boot_debugging.dto.UserDTO;
import spring_boot_debugging.service.UserService2;

import java.util.List;

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

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserControllerV2 userControllerV2;

    @MockitoBean
    private UserService2 userService2;

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

        createUserRequest = CreateUserRequest.builder()
                .username("David")
                .password("david-1234")
                .email("david.doe@example.com")
                .age(27)
                .roles(List.of("USER"))
                .build();
    }

    // [POST] Create User --> 201 OK
    @Test
    @DisplayName("[POST] Create User --> 201 OK")
    void should_create_user_successfully_when_valid_user_is_provided() throws Exception {
        when(userService2.createUser(any())).thenReturn(user1);

        // Act & Assert
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserRequest))
                )
                .andExpect(status().isCreated());

        verify(userService2, times(1)).createUser(any(CreateUserRequest.class));
    }


    // [POST] Create User --> 400 Bad Request
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