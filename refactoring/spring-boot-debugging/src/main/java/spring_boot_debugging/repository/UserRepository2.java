package spring_boot_debugging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_boot_debugging.model.User;

public interface UserRepository2 extends JpaRepository<User, Long> {


}
