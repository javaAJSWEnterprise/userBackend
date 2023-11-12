package com.example.userbackend.dtos;

import com.example.userbackend.model.Authentication;
import com.example.userbackend.validator.AgeConstraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class UserRequest {

    @NotNull(message = "First name cannot be null")
    @NotBlank(message = "First name cannot be blank")
    @Size(max = 25, message = "First name must be at most 25 characters long")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "First name must contain only letters and numbers")
    private String firstname;

    @NotNull(message = "Last name cannot be null")
    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 25, message = "Last name must be at most 25 characters long")
    @Pattern(regexp = "^[\\p{L}0-9]*$", message = "Last name must contain only letters, numbers, and 'ñ'")
    private String lastname;

    @NotNull(message = "Birth date cannot be null")
    @Past(message = "Birth date must be in the past")
    @AgeConstraint(value = 16, message = "User must be at least 16 years old")
    private Date birthDate;



    @Valid
    private Authentication auth;

    public UserRequest(){

    }


}
