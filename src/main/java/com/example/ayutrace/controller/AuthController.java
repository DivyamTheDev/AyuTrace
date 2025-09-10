package com.example.ayutrace.controller;

import com.example.ayutrace.dto.UserDTO.*;
import com.example.ayutrace.model.User;
import com.example.ayutrace.service.JwtUserDetailsService;
import com.example.ayutrace.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        try {
            String username = loginRequest.get("username");
            String password = loginRequest.get("password");

            if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Username and password are required"));
            }

            // Authenticate user
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );

            // Generate JWT token
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String token = jwtTokenUtil.generateToken(userDetails);

            // Get user info
            User user = userDetailsService.getUserByUsername(username);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("type", "Bearer");
            response.put("user", Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "email", user.getEmail(),
                "role", user.getRole().name(),
                "fullName", user.getFullName()
            ));

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid username or password"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Login failed: " + e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDTO userDTO) {
        try {
            // Check if user already exists
            if (userDetailsService.userExists(userDTO.getUsername())) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Username already exists"));
            }

            if (userDetailsService.emailExists(userDTO.getEmail())) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Email already exists"));
            }

            // Create new user
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setFullName(userDTO.getFullName());
            // Set role (UserRegistrationDTO already has UserRole enum)
            user.setRole(userDTO.getRole() != null ? userDTO.getRole() : User.UserRole.FARMER);
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            User savedUser = userDetailsService.saveUser(user);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("user", Map.of(
                "id", savedUser.getId(),
                "username", savedUser.getUsername(),
                "email", savedUser.getEmail(),
                "role", savedUser.getRole().name(),
                "fullName", savedUser.getFullName()
            ));

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Registration failed: " + e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // JWT tokens are stateless, so logout is handled on frontend
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7); // Remove "Bearer "
            String username = jwtTokenUtil.getUsernameFromToken(token);
            
            if (username != null && jwtTokenUtil.validateToken(token, userDetailsService.loadUserByUsername(username))) {
                User user = userDetailsService.getUserByUsername(username);
                
                Map<String, Object> response = Map.of(
                    "id", user.getId(),
                    "username", user.getUsername(),
                    "email", user.getEmail(),
                    "role", user.getRole().name(),
                    "fullName", user.getFullName()
                );
                
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid token"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Authentication failed"));
        }
    }

    // Test endpoint for development
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(Map.of(
            "message", "Auth endpoint working",
            "timestamp", System.currentTimeMillis()
        ));
    }
}
