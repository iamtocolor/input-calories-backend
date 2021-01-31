package com.toptal.project.inputcaloriesapis.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * TODO: Add any hashing criteria
 */
@Service
public class PasswordHasher {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public String hashPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean matchHashedPassword(String password, String hashed) {
        return bCryptPasswordEncoder.matches(password, hashed);
    }
}
