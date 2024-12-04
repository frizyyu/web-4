package com.example.web4spring.controllers;

import com.example.web4spring.model.Stats;
import com.example.web4spring.model.Users;
import com.example.web4spring.service.JwtService;
import com.example.web4spring.service.StatsService;
import com.example.web4spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private StatsService statsService;

    @PostMapping("/login")
    public ResponseEntity<?> authorizeUser(@RequestBody Map<String, String> credentials) {
        System.out.println("XBNKLKCXVBN");
        Users authenticatedUser = new Users();
        if (credentials.get("action").equals("login")) {
            authenticatedUser = userService.getUser(credentials.get("login"), credentials.get("pswd"));
            if (authenticatedUser != null) {
                String token = jwtService.generateToken(authenticatedUser.getUsername());
                authenticatedUser.setPassword("");
                return ResponseEntity.ok(Map.of("token", token, "user", authenticatedUser));
            }
        } else {
            if (userService.getUser(credentials.get("login"), credentials.get("pswd")) == null) {
                authenticatedUser.setUsername(credentials.get("login"));
                authenticatedUser.setPassword(credentials.get("pswd"));
                authenticatedUser = userService.saveUser(authenticatedUser);
                String token = jwtService.generateToken(authenticatedUser.getUsername());
                Stats stats = new Stats();
                stats.setUserId(authenticatedUser.getUid());
                stats.setAlive(0L);
                stats.setDied(0L);
                stats.setScore(0L);
                stats.setUsername(authenticatedUser.getUsername());
                statsService.createStats(stats);
                authenticatedUser.setPassword("");
                return ResponseEntity.ok(Map.of("token", token, "user", authenticatedUser));
            } else {
                authenticatedUser = null;
            }
        }
        return ResponseEntity.noContent().build();
    }
}
