
//date 10/8/25



package com.hexaware.cozyhavenproject.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.hexaware.cozyhavenproject.dto.auth.*;
import com.hexaware.cozyhavenproject.entities.RevokedToken;
import com.hexaware.cozyhavenproject.entities.User;
import com.hexaware.cozyhavenproject.repository.RevokedTokenRepository;
import com.hexaware.cozyhavenproject.repository.UserRepository;
import com.hexaware.cozyhavenproject.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private RevokedTokenRepository revokedTokenRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail()) != null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already in use"));
        }
        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword())); // hash
        u.setContactNumber(req.getContactNumber());
        u.setAddress(req.getAddress());
        u.setGender(req.getGender());
        u.setRole(req.getRole());
        User saved = userRepository.save(u);
        return ResponseEntity.created(URI.create("/api/v1/users/" + saved.getUserId())).body(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
            
            User u = userRepository.findByEmail(req.getEmail());
            Map<String, Object> claims = new HashMap<>();
            claims.put("role", u.getRole().name());
            claims.put("userId", u.getUserId());

            String token = jwtUtil.generateToken(u.getEmail(), claims);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Authorization header missing"));
        }
        String token = authHeader.substring(7);
        RevokedToken rt = new RevokedToken();
        // you'll need a user to set - optional
        rt.setToken(token);
        rt.setRevokedAt(LocalDateTime.now());
        revokedTokenRepository.save(rt);
        return ResponseEntity.ok(Map.of("message", "Logged out"));
    }
}
