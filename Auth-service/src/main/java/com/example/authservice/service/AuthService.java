package com.example.authservice.service;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    @Getter
    private static final AuthService INSTANCE = new AuthService();

    private final Map<String, String> userDatabase = new HashMap<>();
    private final Map<String, String> tokenDatabase = new HashMap<>();

    private AuthService() {
        userDatabase.put("admin", "adminPassword");
        userDatabase.put("user", "userPassword");
    }

    public String authenticate(String username, String password) {
        if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
            String token = generateToken(username);
            tokenDatabase.put(token, username);
            return token;
        }
        return null;
    }

    public boolean validateToken(String token) {
        return tokenDatabase.containsKey(token);
    }

    private String generateToken(String username) {
        String token = "a-level-token" + System.currentTimeMillis();
        if (username.equals("admin")) {
            token += "admin";
        }
        return token;
    }

    public boolean isAdminUser(String token) {
        return token.contains("admin");
    }
}
