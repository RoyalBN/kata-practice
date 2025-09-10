package spring_boot_debugging.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import spring_boot_debugging.dto.CreateUserRequest;
import spring_boot_debugging.dto.UpdateUserRequest;
import spring_boot_debugging.dto.UserDTO;
import spring_boot_debugging.dto.UserStatisticsResponse;
import spring_boot_debugging.service.UserService2;

import java.util.List;

@RestController
@RequestMapping("/v1/api/users")
public class UserControllerV2 {

    private final UserService2 userService2;

    public UserControllerV2(UserService2 userService2) {
        this.userService2 = userService2;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid CreateUserRequest user) {
        UserDTO createdUser = userService2.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable @Positive(message = "Id must be positive") Long id) {
        UserDTO user = userService2.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService2.getAllUsers());
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable @Positive(message = "Id must be positive") Long id,
            @RequestBody @Valid UpdateUserRequest updateUserRequest
    ) {
        UserDTO updatedUser = userService2.updateUser(id, updateUserRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable @Positive(message = "Id must be positive") Long id) {
        userService2.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<Page<UserDTO>> searchUser(
            @RequestParam @NotBlank(message = "Username is required") String username,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(username).ascending());
        Page<UserDTO> dtoPage = userService2.searchUser(username, pageable);
        return ResponseEntity.ok(dtoPage);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/stats")
    public ResponseEntity<UserStatisticsResponse> getUserStatistics() {
        long totalUsers = userService2.getAllUsers().size();
        long adultUsers = userService2.countAdultUsers();
        double adultUsersPercentage = totalUsers > 0 ? (adultUsers * 100.0) / totalUsers : 0;

        UserStatisticsResponse userStatisticsResponse = UserStatisticsResponse.builder()
                .totalUsers(totalUsers)
                .adultUsers(adultUsers)
                .adultUsersPercentage(adultUsersPercentage)
                .build();

        return ResponseEntity.ok(userStatisticsResponse);
    }

}