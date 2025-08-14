package com.example.spring_boot_controller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserControllerDirty {

    @Autowired
    private Connection connection; // ❌ Mauvaise pratique : dépendance directe à la DB

    @GetMapping("/getuser")
    public Map<String, Object> getUser(@RequestParam String id) {
        Map<String, Object> result = new HashMap<>();
        try {
            Statement stmt = connection.createStatement(); // ❌ Pas de PreparedStatement => risque injection SQL
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id = '" + id + "'");
            if (rs.next()) {
                result.put("id", rs.getInt("id"));
                result.put("name", rs.getString("name"));
                result.put("email", rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // ❌ Pas de gestion d'erreur appropriée
        }
        return result; // ❌ Retourne directement la map => manque d'objet métier
    }

    @PostMapping("/adduser")
    public String addUser(@RequestParam String name, @RequestParam String email) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("INSERT INTO users (name, email) VALUES ('" + name + "', '" + email + "')");
        } catch (SQLException e) {
            return "error"; // ❌ Message d'erreur trop vague
        }
        return "ok";
    }
}
