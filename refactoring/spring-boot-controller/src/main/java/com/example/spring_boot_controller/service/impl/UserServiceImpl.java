package com.example.spring_boot_controller.service.impl;

import com.example.spring_boot_controller.dto.UserCreateRequest;
import com.example.spring_boot_controller.dto.UserDTO;
import com.example.spring_boot_controller.exceptions.UserNotFoundException;
import com.example.spring_boot_controller.mapper.UserMapper;
import com.example.spring_boot_controller.model.User;
import com.example.spring_boot_controller.repository.UserRepository;
import com.example.spring_boot_controller.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        return userMapper.toDTO(user);
    }

    @Transactional
    public UserDTO addUser(UserCreateRequest user) {
        User userToSave = userMapper.toEntity(user);
        User savedUser = userRepository.save(userToSave);
        return userMapper.toDTO(savedUser);
    }
}
