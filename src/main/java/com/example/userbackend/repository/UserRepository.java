package com.example.userbackend.repository;

import com.example.userbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByAuthEmail(String email);
}
