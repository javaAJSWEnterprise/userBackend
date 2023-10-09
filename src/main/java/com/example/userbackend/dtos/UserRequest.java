package com.example.userbackend.dtos;

import com.example.userbackend.model.Authentication;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class UserRequest {

    private String firstname;
    private String lastname;
    private Authentication auth;
    private Date birthDate;

    public UserRequest(){

    }
}
