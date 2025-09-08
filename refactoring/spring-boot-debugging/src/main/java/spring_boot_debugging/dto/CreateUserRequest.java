package spring_boot_debugging.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record CreateUserRequest(
    @NotBlank(message = "username is required")
    @Size(min = 3, max = 50, message = "username must be between 3 and 50 characters")
    String username,

    @NotBlank(message = "password is required")
    @Size(min = 8, message = "password must be at least 8 characters long")
    String password,

    @NotBlank(message = "email is required")
    @Email
    String email,

    @Past(message = "birthDate must be in the past")
    LocalDate birthDate,

    List<String> roles,

    @Min(value = 18, message = "age must be at least 18")
    @Max(value = 120, message = "age must be at most 120")
    Integer age
) {}
