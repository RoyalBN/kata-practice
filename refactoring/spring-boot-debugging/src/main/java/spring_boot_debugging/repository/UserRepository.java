package spring_boot_debugging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring_boot_debugging.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Problème : Requête non optimisée avec LIKE sans index
    @Query("SELECT u FROM User u WHERE u.username LIKE %:username%")
    List<User> findByUsernameContaining(@Param("username") String username);
    
    // Problème : Pas de pagination pour une requête qui pourrait retourner beaucoup de résultats
    @Query("SELECT u FROM User u WHERE u.email IS NOT NULL")
    List<User> findAllWithEmail();
    
    // Problème : Requête N+1 potentielle
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles")
    List<User> findAllWithRoles();
    
    // Problème : Pas de gestion des doublons potentiels
    User findByEmail(String email);
    
    // Problème : Suppression en masse sans transaction
    void deleteByEmailContaining(String domain);
}
