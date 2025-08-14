package com.example.spring_boot_controller.service;


import com.example.spring_boot_controller.dto.UserCreateRequest;
import com.example.spring_boot_controller.dto.UserDTO;

import java.util.List;

public interface IUserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO addUser(UserCreateRequest user);
}
