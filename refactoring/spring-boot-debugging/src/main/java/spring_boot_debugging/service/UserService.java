package spring_boot_debugging.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring_boot_debugging.model.User;
import spring_boot_debugging.repository.UserRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    // Problème : Pas de gestion de la concurrence
    public User createUser(User user) {
        // Problème : Pas de validation des données
        user.setAge(calculateAge(user.getBirthDate()));
        return userRepository.save(user);
    }
    
    // Problème : Mauvaise gestion des erreurs
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    // Problème : Pas de pagination
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    // Problème : Pas de vérification de l'existence de l'utilisateur
    public void updateUser(User user) {
        userRepository.save(user);
    }
    
    // Problème : Suppression sans vérification des dépendances
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    // Problème : Logique de calcul d'âge incorrecte
    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) return 0;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
    
    // Problème : Méthode non sécurisée qui expose trop de données
    public List<User> searchUsers(String keyword) {
        return userRepository.findByUsernameContaining(keyword);
    }
    
    // Problème : Opération coûteuse sans mise en cache
    public long countAdultUsers() {
        return getAllUsers().stream()
                .filter(user -> user.getAge() != null && user.getAge() >= 18)
                .count();
    }
}
