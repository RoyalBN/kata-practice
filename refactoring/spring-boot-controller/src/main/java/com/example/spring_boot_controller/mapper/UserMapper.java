package com.example.spring_boot_controller.mapper;

import com.example.spring_boot_controller.dto.UserCreateRequest;
import com.example.spring_boot_controller.dto.UserDTO;
import com.example.spring_boot_controller.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Mapping User --> UserDTO
    UserDTO toDTO(User user);

    // Mapping UserDTO --> User
    @Mapping(target = "id", ignore = true)
    User toEntity(UserCreateRequest userCreateRequest);
}
