package spring_boot_debugging.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;  // Problème : Mot de passe en clair
    
    private String email;
    
    @Column(name = "birth_date")
    private LocalDate birthDate;
    
    @ElementCollection
    private List<String> roles;  // Problème : Pas de relation many-to-many avec une table de rôles

    private Integer age;  // Problème : Champ calculé non mis à jour
    
    // Problème : Pas de constructeur par défaut explicite
    
    // Problème : Pas de validation des champs
    
    // Problème : Pas de méthode equals/hashCode personnalisée
}
