package com.example.userbackend.service;

import com.example.userbackend.dtos.LoginResponse;
import com.example.userbackend.dtos.UserRequest;
import com.example.userbackend.dtos.UserResponse;
import com.example.userbackend.dtos.UserUpdateRequest;
import com.example.userbackend.exception.InvalidUserExeption;
import com.example.userbackend.model.Authentication;
import com.example.userbackend.model.User;
import com.example.userbackend.repository.UserRepository;
import com.example.userbackend.security.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final EncryptionService encryptionService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, EncryptionService encryptionService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public User mapUserRequestToUser(UserRequest userRequest) {
        return modelMapper.map(userRequest, User.class);
    }

    public UserResponse mapUserToUserResponse(User user) {
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        userResponse.setEmail(user.getAuth().getEmail());
        userResponse.setName(user.getFirstname() + " " + user.getLastname());
        return userResponse;
    }

    public UserResponse createUser(UserRequest user) {
        Optional<User> userByEmail = userRepository.findByAuthEmail(user.getAuth().getEmail());
        if (userByEmail.isPresent()) {
            throw new InvalidUserExeption("Email is already in use.");
        }
        encryptionService.hashingPassword(user);
        User userCreated = userRepository.save(mapUserRequestToUser(user));
        return mapUserToUserResponse(userCreated);
    }

    public Optional<UserResponse> getUserById(String userId) {
        Optional<User> userFind = userRepository.findById(userId);
        if (userFind.isPresent()) {
            UserResponse userResponse = mapUserToUserResponse(userFind.get());
            return Optional.ofNullable(userResponse);
        } else {
            return Optional.empty();
        }
    }

    public UserResponse updateUser(String userId, UserUpdateRequest user) {
        Optional<User> existingUserOptional = userRepository.findById(userId);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setFirstname(user.getFirstname());
            existingUser.setLastname(user.getLastname());
            existingUser.setBirthDate(user.getBirthDate());

            User userUpdated = userRepository.save(existingUser);
            return mapUserToUserResponse(userUpdated);
        } else {
            return null;
        }
    }

    public LoginResponse login(Authentication auth) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(auth.getEmail(), auth.getPassword())
        );

        var user = userRepository.findByAuthEmail(auth.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        var jwtToken = jwtService.generateToken(user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);

        return loginResponse;
    }
}
