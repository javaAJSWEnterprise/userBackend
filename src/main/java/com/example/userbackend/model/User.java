package com.example.userbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "firstname", length = 25)
    private String firstname;

    @Column(name = "lastname", length = 25)
    private String lastname;

    @Embedded
    private Authentication auth;

    @Column(name = "birthDate")
    private Date birthDate;

    public User() {

    }
}
