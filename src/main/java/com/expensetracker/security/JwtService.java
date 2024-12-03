package com.expensetracker.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Generate a secure secret key using Keys class
    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(SECRET_KEY) // Use the key directly here
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder() // Use parserBuilder instead of parser
                .setSigningKey(SECRET_KEY) // Set the key here
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
