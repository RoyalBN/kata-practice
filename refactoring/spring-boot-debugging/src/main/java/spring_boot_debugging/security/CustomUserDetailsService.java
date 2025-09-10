package spring_boot_debugging.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring_boot_debugging.model.User;
import spring_boot_debugging.repository.UserRepository2;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository2 userRepository2;

    public CustomUserDetailsService(UserRepository2 userRepository2) {
        this.userRepository2 = userRepository2;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository2.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList()
        );
    }
}
