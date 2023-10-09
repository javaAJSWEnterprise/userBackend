package com.example.userbackend.controller;

import com.example.userbackend.dtos.UserRequest;
import com.example.userbackend.dtos.UserResponse;
import com.example.userbackend.model.User;
import com.example.userbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest user) {
        return userService.createUser(user);
    }


    @GetMapping("/{userId}")
    public Optional<UserResponse> getUserById(@PathVariable String userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable String userId, @RequestBody UserRequest user) {
        return userService.updateUser(userId, user);
    }

}
