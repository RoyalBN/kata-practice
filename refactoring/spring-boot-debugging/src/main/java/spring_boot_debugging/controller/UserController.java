package spring_boot_debugging.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring_boot_debugging.model.User;
import spring_boot_debugging.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Problème : Pas de validation des entrées
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // Problème : Pas de vérification des doublons
        return ResponseEntity.ok(userService.createUser(user));
    }

    // Problème : Exposition de l'ID dans l'URL
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        // Problème : Mauvaise gestion des cas où l'utilisateur n'existe pas
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // Problème : Pas de pagination
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Problème : Pas de vérification des autorisations
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        // Problème : Pas de vérification de l'ID dans le corps
        user.setId(id);
        userService.updateUser(user);
        return ResponseEntity.ok(user);
    }

    // Problème : Pas de vérification des dépendances avant suppression
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Problème : Endpoint potentiellement coûteux
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String keyword) {
        // Problème : Pas de limite sur la taille du résultat
        return ResponseEntity.ok(userService.searchUsers(keyword));
    }

    // Problème : Endpoint exposant des informations sensibles
    @GetMapping("/stats")
    public ResponseEntity<String> getUserStats() {
        long totalUsers = userService.getAllUsers().size();
        long adultUsers = userService.countAdultUsers();
        // Problème : Pas de formatage approprié des données
        return ResponseEntity.ok("Total users: " + totalUsers + ", Adult users: " + adultUsers);
    }
}
