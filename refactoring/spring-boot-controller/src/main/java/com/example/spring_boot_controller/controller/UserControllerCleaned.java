package com.example.spring_boot_controller.controller;

import com.example.spring_boot_controller.dto.UserCreateRequest;
import com.example.spring_boot_controller.dto.UserDTO;
import com.example.spring_boot_controller.model.User;
import com.example.spring_boot_controller.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequestMapping("v2/api/users")
@RestController

public class UserControllerCleaned {

    private final IUserService userService;

    @Autowired
    public UserControllerCleaned(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }


    @PostMapping
    public UserDTO addUser(@Valid @RequestBody UserCreateRequest user) {
        return userService.addUser(user);
    }
}
