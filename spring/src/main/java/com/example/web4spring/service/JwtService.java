package com.example.web4spring.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "frizyy_lab4_frizyy_lab4_frizyy_lab4_frizyy_lab4_frizyy_lab4_frizyy_lab4_frizyy_lab4_frizyy_lab4_frizyy_lab4_frizyy_lab4_frizyy_lab4_frizyy_lab4_frizyy_lab4";
    private static final long EXPIRATION_TIME = 86400000; // 1 день (мс)

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Записываем имя пользователя в поле subject
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes())
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseSignedClaims(token)
                .getBody()
                .getSubject(); // Извлекаем имя пользователя из поля subject
    }

    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseSignedClaims(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}
