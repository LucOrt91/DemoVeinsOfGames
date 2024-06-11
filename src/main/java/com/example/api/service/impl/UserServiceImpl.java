package com.example.api.service.impl;

import com.example.api.model.User;
import com.example.api.repository.UserRepository;
import com.example.api.security.dto.validation.UserCreateDto;
import com.example.api.security.dto.validation.UserProfileUpdateDto;
import com.example.api.service.PasswordEncryptionService;
import com.example.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncryptionService passwordEncryptionService;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User createUser(UserCreateDto newUser) {
        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setPassword(passwordEncryptionService.encryptPassword(newUser.getPassword()));
        user.setRolId(newUser.getRolId());
        return userRepository.save(user);
    }

    @Override
    public void updateUserProfile(User user, UserProfileUpdateDto updates) {
        user.setUsername(updates.getUsername());
        user.setEmail(updates.getEmail());
        if (updates.getPassword() != null && !updates.getPassword().isEmpty()) {
            user.setPassword(passwordEncryptionService.encryptPassword(updates.getPassword()));
        }
        user.setRolId(updates.getRolId());
        userRepository.save(user);
    }
}
