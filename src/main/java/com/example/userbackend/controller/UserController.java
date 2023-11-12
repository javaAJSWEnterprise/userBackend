package com.example.userbackend.controller;

import com.example.userbackend.dtos.LoginResponse;
import com.example.userbackend.dtos.UserRequest;
import com.example.userbackend.dtos.UserResponse;
import com.example.userbackend.dtos.UserUpdateRequest;
import com.example.userbackend.exception.InvalidUserExeption;
import com.example.userbackend.model.Authentication;
import com.example.userbackend.model.ErrorResponse;
import com.example.userbackend.security.JwtAuthenticationFilter;
import com.example.userbackend.security.JwtService;
import com.example.userbackend.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;



@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {

        this.userService = userService;
        this.jwtService = jwtService;
    }
    @PostMapping("/register")
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
    @GetMapping("")

    public ResponseEntity<?> getUserById(@RequestHeader(name = "Authorization") String authorizationHeader) {
        try {


            String token = authorizationHeader.substring(7);

            Claims  claims = jwtService.extractAllClaims(token);

            String userId = (String) claims.get("id");

            Optional<UserResponse> user = userService.getUserById(userId);

            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User not found"));
            }
            return ResponseEntity.ok(user.get());
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
        }
    }
    @PutMapping("")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateRequest user, @RequestHeader(name = "Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.substring(7);

            Claims  claims = jwtService.extractAllClaims(token);

            String userId = (String) claims.get("id");

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
            LoginResponse user = userService.login(authentication);

            return ResponseEntity.ok(user);

        }
        catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
        }
    }
}
