package com.example.userbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Embeddable
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class Authentication {

    @NotNull(message = "Email field cannot be null")
    @NotBlank(message = "Email field cannot be blank")
    @Size(max = 50, message = "Email field must be at most 50 characters long")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email field must be a valid email address")
    @Column(name = "email", length = 50)
    private String email;

    @NotNull(message = "Password field cannot be null")
    @NotBlank(message = "Password field cannot be blank")
    @Size(min = 8, message = "Password field must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+={}.]).*$", message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    @Column(name = "password")
    private String password;


    public Authentication() {

    }
}
