package com.example.userbackend.model;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class Authentication {

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "password")
    private String password;

    public Authentication() {

    }
}
