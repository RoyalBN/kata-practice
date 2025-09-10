package spring_boot_debugging.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import spring_boot_debugging.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository2 extends JpaRepository<User, Long> {
    Optional<User> findByUsername(@Param("username") String username);
    Page<User> findByUsernameContaining(String username, Pageable pageable);
    Page<User> findAll(Pageable pageable);
    List<User> findByEmailIsNotNull();
    Integer countByAgeGreaterThanEqual(int age);

    @EntityGraph(attributePaths = "roles")
    List<User> findAllByRoles(String role);

    Optional<User> findByEmail(String email);

    @Transactional
    void deleteByEmailContaining(String domain);
}
