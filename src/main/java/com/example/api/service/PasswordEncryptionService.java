package com.example.api.service;

public interface PasswordEncryptionService {
    String encryptPassword(String rawPassword);

    boolean matchPassword(String rawPassword, String encodedPassword);
}

