package com.example.ayutrace.config;

import com.example.ayutrace.model.User;
import com.example.ayutrace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            // Create test users if they don't exist
            createTestUserIfNotExists("farmer", "farmer@ayutrace.com", "Ravi Kumar", User.UserRole.FARMER);
            createTestUserIfNotExists("lab", "lab@ayutrace.com", "Dr. Priya Sharma", User.UserRole.LAB_TECHNICIAN);
            createTestUserIfNotExists("processor", "processor@ayutrace.com", "Amit Patel", User.UserRole.PROCESSOR);
            createTestUserIfNotExists("admin", "admin@ayutrace.com", "Admin User", User.UserRole.ADMIN);
            
            System.out.println("✅ Test users initialized!");
            System.out.println("🌱 Farmer: farmer / password123");
            System.out.println("🔬 Lab: lab / password123");
            System.out.println("⚙️ Processor: processor / password123");
            System.out.println("👥 Admin: admin / password123");
        };
    }

    private void createTestUserIfNotExists(String username, String email, String fullName, User.UserRole role) {
        try {
            if (!userRepository.findByUsername(username).isPresent()) {
                User user = new User();
                user.setUsername(username);
                user.setEmail(email);
                user.setFullName(fullName);
                user.setRole(role);
                user.setPassword(passwordEncoder.encode("password123"));
                user.setIsActive(true);
                user.setIsVerified(true);
                user.setPhoneNumber("+91-9876543210");
                user.setAddress("Test Address");
                user.setCity("Delhi");
                user.setState("Delhi");
                user.setPinCode("110001");
                
                User savedUser = userRepository.save(user);
                System.out.println("✅ Created test user: " + username + " (" + role + ") - ID: " + savedUser.getId());
            } else {
                System.out.println("ℹ️ Test user already exists: " + username);
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to create user " + username + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
