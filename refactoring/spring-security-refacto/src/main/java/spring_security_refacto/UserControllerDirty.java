package spring_security_refacto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class UserControllerDirty {

    @Autowired
    private Connection connection; // 😱 Accès direct à la DB

    // Endpoint accessible à tout le monde, même sans login
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        try {
            Statement stmt = connection.createStatement();
            // Stocke le mot de passe en clair et aucune validation de rôle
            stmt.executeUpdate("INSERT INTO users (username, password, role) VALUES ('" + username + "', '" + password + "', '" + role + "')");
        } catch (SQLException e) {
            return "error";
        }
        return "ok";
    }

    // Endpoint censé être protégé (ADMIN uniquement)
    @GetMapping("/admin/all-users")
    public List<Map<String, Object>> getAllUsers() {
        List<Map<String, Object>> users = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                Map<String, Object> user = new HashMap<>();
                user.put("id", rs.getInt("id"));
                user.put("username", rs.getString("username"));
                user.put("password", rs.getString("password")); // ❌ Expose le mot de passe
                user.put("role", rs.getString("role"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Endpoint censé être protégé (USER + ADMIN)
    @GetMapping("/user/profile")
    public Map<String, Object> getProfile(@RequestParam String username) {
        Map<String, Object> profile = new HashMap<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username = '" + username + "'");
            if (rs.next()) {
                profile.put("id", rs.getInt("id"));
                profile.put("username", rs.getString("username"));
                profile.put("role", rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profile;
    }

    // Pas de logout / refresh token / login sécurisé
}
