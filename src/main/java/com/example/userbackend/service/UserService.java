package com.example.userbackend.service;

import com.example.userbackend.dtos.LoginResponse;
import com.example.userbackend.dtos.UserRequest;
import com.example.userbackend.dtos.UserResponse;
import com.example.userbackend.dtos.UserUpdateRequest;
import com.example.userbackend.model.Authentication;

import java.util.Optional;

public interface UserService {
    UserResponse createUser(UserRequest user);
    Optional<UserResponse> getUserById(String userId);
    UserResponse updateUser(String userId, UserUpdateRequest user);
    LoginResponse login(Authentication auth);
}
