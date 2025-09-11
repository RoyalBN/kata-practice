package spring_boot_debugging.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring_boot_debugging.dto.CreateUserRequest;
import spring_boot_debugging.dto.UpdateUserRequest;
import spring_boot_debugging.dto.UserDTO;
import spring_boot_debugging.exception.UserAlreadyExistsException;
import spring_boot_debugging.model.User;
import spring_boot_debugging.repository.UserRepository2;

@Service
public class UserService2 {

    private final UserRepository2 userRepository;

    private UserService2(UserRepository2 userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(CreateUserRequest user) {

        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + user.getEmail() + " already exists");
        }

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

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(user -> UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .age(user.getAge())
                .build());
    }

    public UserDTO updateUser(Long userId, UpdateUserRequest updateUserRequest) {
        User foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + userId + " not found"));

        User userToUpdate = User.builder()
                .id(foundUser.getId())
                .username(updateUserRequest.getUsername())
                .email(updateUserRequest.getEmail())
                .age(updateUserRequest.getAge())
                .roles(foundUser.getRoles())
                .build();

        User userUpdated = userRepository.save(userToUpdate);

        return UserDTO.builder()
                .id(userUpdated.getId())
                .username(userUpdated.getUsername())
                .email(userUpdated.getEmail())
                .age(userUpdated.getAge())
                .build();
    }

    public void deleteUserById(Long userId) {
        User foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + userId + " not found"));
        userRepository.delete(foundUser);
    }

    public long countAllUsers() {
        return userRepository.findAll().size();
    }

    public Integer countAdultUsers() {
        return userRepository.countByAgeGreaterThanEqual(18);
    }

    public Page<UserDTO> searchUser(String username, Pageable pageable) {
        Page<User> users = userRepository.findByUsernameContaining(username, pageable);

        return users.map(user -> UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .age(user.getAge())
                .build());
    }
}
