package com.example.userbackend.controller;

import com.example.userbackend.dtos.UserRequest;
import com.example.userbackend.dtos.UserResponse;
import com.example.userbackend.dtos.UserUpdateRequest;
import com.example.userbackend.exception.InvalidUserExeption;
import com.example.userbackend.model.Authentication;
import com.example.userbackend.model.ErrorResponse;
import com.example.userbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest user) {
        try {
            UserResponse userCreated = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
        } catch (InvalidUserExeption e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
        }
    }
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        try {
            Optional<UserResponse> user = userService.getUserById(userId);
            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User not found"));
            }
            return ResponseEntity.ok(user.get());
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
        }
    }
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateRequest user, @PathVariable String userId) {
        try {
            UserResponse updatedUser = userService.updateUser(userId, user);
            if (updatedUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User not found"));
            }
            return ResponseEntity.ok(updatedUser);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Authentication authentication) {
        try {
            Optional<UserResponse> user = userService.login(authentication);
            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Authentication failed"));
            }
            return ResponseEntity.ok(user.get());
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
        }
    }
}
