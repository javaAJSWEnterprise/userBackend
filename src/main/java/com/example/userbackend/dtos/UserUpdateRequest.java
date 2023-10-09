package com.example.userbackend.dtos;

import lombok.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class UserUpdateRequest {
    private String firstname;
    private String lastname;
    private Date birthDate;
    public UserUpdateRequest(){

    }
}
