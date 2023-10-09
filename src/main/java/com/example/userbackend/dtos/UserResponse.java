package com.example.userbackend.dtos;

import com.example.userbackend.model.Authentication;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private Date birthDate;

    public UserResponse(){

    }
}
