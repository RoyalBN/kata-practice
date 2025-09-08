package spring_boot_debugging.dto;

public record UserDTO (
    Long id,
    String username,
    String email,
    Integer age
) {}
