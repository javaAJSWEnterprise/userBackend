package com.example.userbackend.service;

import com.example.userbackend.dtos.UserRequest;
import com.example.userbackend.dtos.UserResponse;
import com.example.userbackend.dtos.UserUpdateRequest;
import com.example.userbackend.model.Authentication;
import com.example.userbackend.model.User;
import com.example.userbackend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
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
        if(userByEmail.isPresent()){
            return null;
        }
        EncryptionService.hashingPassword(user);
        User userCreated = userRepository.save(mapUserRequestToUser(user));
        return  mapUserToUserResponse(userCreated);
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
            return  mapUserToUserResponse(userUpdated);
        } else {
           return null;
        }
    }

    public Optional<UserResponse> login(Authentication auth){
        Optional<User> userLogin = userRepository.findByAuthEmail(auth.getEmail());

        if(userLogin.isEmpty()){
            return Optional.empty();
        }

        if(!EncryptionService.authenticateUser(userLogin.get().getAuth().getPassword(), auth.getPassword())){
            return Optional.empty();
        }

        return Optional.ofNullable(mapUserToUserResponse(userLogin.get()));
    }
}

