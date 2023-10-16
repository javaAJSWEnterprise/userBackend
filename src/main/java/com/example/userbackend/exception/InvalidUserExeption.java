package com.example.userbackend.exception;

public class InvalidUserExeption extends RuntimeException{
    public InvalidUserExeption(String message) {
        super(message);
    }
}
