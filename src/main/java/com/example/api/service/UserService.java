package com.example.api.service;


import com.example.api.model.User;
import com.example.api.security.dto.validation.UserCreateDto;
import com.example.api.security.dto.validation.UserProfileUpdateDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);

    Optional<User> findById(Long id);

    List<User> findAll();

    User findByUsername(String username);

    boolean existsByUsername(String username);

    void deleteById(Long id);

    User getCurrentUser();

    User createUser(UserCreateDto newUser);

    void updateUserProfile(User user, UserProfileUpdateDto updates);
}
