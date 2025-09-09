package spring_boot_debugging.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
    @Size(min = 3, max = 50, message = "username must be between 3 and 50 characters")
    private String username;

    @Size(min = 8, message = "password must be at least 8 characters long")
    private String password;

    @Email
    private String email;

    @Past(message = "birthDate must be in the past")
    private LocalDate birthDate;

    private List<String> roles;

    @Min(value = 18, message = "age must be at least 18")
    @Max(value = 120, message = "age must be at most 120")
    private Integer age;
}
