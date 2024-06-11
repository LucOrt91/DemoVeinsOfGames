package com.example.api.controller;

import com.example.api.model.User;
import com.example.api.security.dto.validation.UserCreateDto;
import com.example.api.security.dto.validation.UserProfileUpdateDto;
import com.example.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Endpoint para obtener todos los usuarios.
     */
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    /**
     * Endpoint para obtener el perfil del usuario.
     */
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile() {
        Map<String, Object> response = new HashMap<>();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findByUsername(username);

            if (user == null) {
                response.put("mensaje", "Usuario no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            response.put("mensaje", "Error al obtener información del usuario");
            response.put("Error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Endpoint para crear un nuevo usuario.
     */
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateDto newUser) {
        try {
            User createdUser = userService.createUser(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating user: " + e.getMessage());
        }
    }

    /**
     * Endpoint para actualizar el perfil del usuario.
     */
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UserProfileUpdateDto updates) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User currentUser = userService.findByUsername(username);

        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }

        userService.updateUserProfile(currentUser, updates);

        return ResponseEntity.ok("Profile updated successfully!");
    }

    /**
     * Endpoint para eliminar el perfil del usuario.
     */
    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User currentUser = userService.findByUsername(username);

        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }

        userService.deleteById(currentUser.getId());

        return ResponseEntity.ok("Profile deleted successfully!");
    }
}
