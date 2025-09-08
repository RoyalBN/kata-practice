package spring_boot_debugging.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_boot_debugging.dto.CreateUserRequest;
import spring_boot_debugging.dto.UserDTO;
import spring_boot_debugging.service.UserService;
import spring_boot_debugging.service.UserService2;

import java.net.URI;

@RestController
@RequestMapping("/v1/api/users")
public class UserControllerV2 {

    private final UserService2 userService2;

    private UserControllerV2 (UserService2 userService2) {
        this.userService2 = userService2;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserRequest user) {
        UserDTO created = userService2.createUser(user);
        URI location = URI.create("/v1/api/users/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

}
