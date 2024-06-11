package com.example.api.security.controller;

import com.example.api.model.User;
import com.example.api.security.dto.JwtResponse;
import com.example.api.security.dto.LoginRequest;
import com.example.api.security.dto.RegisterRequest;
import com.example.api.security.jwt.JwtTokenUtil;
import com.example.api.service.PasswordEncryptionService;
import com.example.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @Autowired
    private PasswordEncryptionService encryptionService;

    /**
     * Endpoint para registrar un nuevo usuario.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        if (userService.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setRolId(registerRequest.getRolId());
        user.setPassword(encryptionService.encryptPassword(registerRequest.getPassword()));

        userService.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    /**
     * Endpoint para autenticar un usuario y generar un token JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByUsername(loginRequest.getUsername());
        if (user == null || !encryptionService.matchPassword(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }

        String jwt = jwtUtil.generateTokenForUser(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRolId()
        );

        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}
