package com.example.api.controllerTest;

import com.example.api.controller.UserController;
import com.example.api.model.User;
import com.example.api.security.dto.validation.UserCreateDto;
import com.example.api.security.dto.validation.UserProfileUpdateDto;
import com.example.api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(new User(1L, "user1", "password", "user1@example.com", "ROLE_USER"));
        when(userService.findAll()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getAllUsers();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(users, response.getBody());
    }

    @Test
    void testGetUserProfile() {
        User user = new User(1L, "user1", "password", "user1@example.com", "ROLE_USER");
        when(authentication.getName()).thenReturn("user1");
        when(userService.findByUsername("user1")).thenReturn(user);

        ResponseEntity<?> response = userController.getUserProfile();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
    }

    @Test
    void testCreateUser() {
        UserCreateDto newUser = new UserCreateDto("user2", "password", "user2@example.com", "ROLE_USER");
        User createdUser = new User(2L, "user2", "password", "user2@example.com", "ROLE_USER");
        when(userService.createUser(newUser)).thenReturn(createdUser);

        ResponseEntity<?> response = userController.createUser(newUser);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(createdUser, response.getBody());
    }

    @Test
    void testUpdateProfile() {
        UserProfileUpdateDto updates = new UserProfileUpdateDto("newUser", "newEmail@example.com", "newPassword", "ROLE_USER");
        User currentUser = new User(1L, "user1", "password", "user1@example.com", "ROLE_USER");
        when(authentication.getName()).thenReturn("user1");
        when(userService.findByUsername("user1")).thenReturn(currentUser);

        ResponseEntity<?> response = userController.updateProfile(updates);
        verify(userService, times(1)).updateUserProfile(currentUser, updates);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Profile updated successfully!", response.getBody());
    }

    @Test
    void testDeleteProfile() {
        User currentUser = new User(1L, "user1", "password", "user1@example.com", "ROLE_USER");
        when(authentication.getName()).thenReturn("user1");
        when(userService.findByUsername("user1")).thenReturn(currentUser);

        ResponseEntity<?> response = userController.deleteProfile();
        verify(userService, times(1)).deleteById(currentUser.getId());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Profile deleted successfully!", response.getBody());
    }
}
