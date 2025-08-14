package com.example.spring_boot_controller.repository;

import com.example.spring_boot_controller.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
