package com.example.userbackend.service;

import com.example.userbackend.dtos.UserRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


@Service
public class EncryptionServiceImpl implements EncryptionService {

    public void hashingPassword(UserRequest user){
        String salt = generateSalt();
        String hashPassword = BCrypt.hashpw(user.getAuth().getPassword(), salt);
        user.getAuth().setPassword(hashPassword);
    }
    private static String generateSalt() {
        // Genera un salt aleatorio
        return BCrypt.gensalt(12);
    }

    public boolean authenticateUser(String storedPassword, String enteredPassword) {
        return BCrypt.checkpw(enteredPassword, storedPassword);
    }

}
