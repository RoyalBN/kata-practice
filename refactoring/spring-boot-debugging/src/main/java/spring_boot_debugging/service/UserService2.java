package spring_boot_debugging.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring_boot_debugging.dto.CreateUserRequest;
import spring_boot_debugging.dto.UserDTO;
import spring_boot_debugging.model.User;
import spring_boot_debugging.repository.UserRepository2;

@Service
public class UserService2 {

    private final UserRepository2 userRepository;

    private UserService2(UserRepository2 userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(CreateUserRequest user) {

        User userToCreate = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .age((user.getAge()))
                .roles(user.getRoles())
                .build();

        User userCreated = userRepository.save(userToCreate);

        return UserDTO.builder()
                .id(userCreated.getId())
                .username(userCreated.getUsername())
                .email(userCreated.getEmail())
                .age(userCreated.getAge())
                .build();

    }

    public UserDTO getUserById(Long id) {
        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not found"));

        return UserDTO.builder()
                .id(foundUser.getId())
                .username(foundUser.getUsername())
                .email(foundUser.getEmail())
                .age(foundUser.getAge())
                .build();
    }
}
