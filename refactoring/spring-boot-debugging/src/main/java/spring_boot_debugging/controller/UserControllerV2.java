package spring_boot_debugging.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring_boot_debugging.dto.CreateUserRequest;
import spring_boot_debugging.dto.UserDTO;
import spring_boot_debugging.model.User;
import spring_boot_debugging.service.UserService;
import spring_boot_debugging.service.UserService2;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/api/users")
public class UserControllerV2 {

    private final UserService2 userService2;

    public UserControllerV2 (UserService2 userService2) {
        this.userService2 = userService2;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserRequest user) {
        UserDTO createdUser = userService2.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService2.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService2.getAllUsers());
    }

}
