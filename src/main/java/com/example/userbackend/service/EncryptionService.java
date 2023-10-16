package com.example.userbackend.service;

import com.example.userbackend.dtos.UserRequest;

public interface EncryptionService{
    boolean authenticateUser(String storedPassword, String enteredPassword);
    void hashingPassword(UserRequest user);
}
