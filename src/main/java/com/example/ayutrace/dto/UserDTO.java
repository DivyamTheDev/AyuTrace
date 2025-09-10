package com.example.ayutrace.dto;

import com.example.ayutrace.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class UserDTO {
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserRegistrationDTO {
        
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        private String username;
        
        @NotBlank(message = "Email is required")
        @Email(message = "Please provide a valid email address")
        private String email;
        
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        private String password;
        
        @NotBlank(message = "Full name is required")
        private String fullName;
        
        @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Please provide a valid phone number")
        private String phoneNumber;
        
        @NotNull(message = "Role is required")
        private User.UserRole role;
        
        private String organizationName;
        private String licenseNumber;
        private String address;
        private String city;
        private String state;
        
        @Pattern(regexp = "^[0-9]{6}$", message = "PIN code must be 6 digits")
        private String pinCode;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserLoginDTO {
        
        @NotBlank(message = "Username or email is required")
        private String usernameOrEmail;
        
        @NotBlank(message = "Password is required")
        private String password;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserResponseDTO {
        private Long id;
        private String username;
        private String email;
        private String fullName;
        private String phoneNumber;
        private User.UserRole role;
        private String organizationName;
        private String licenseNumber;
        private String address;
        private String city;
        private String state;
        private String pinCode;
        private Boolean isActive;
        private Boolean isVerified;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserProfileUpdateDTO {
        
        @NotBlank(message = "Full name is required")
        private String fullName;
        
        @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Please provide a valid phone number")
        private String phoneNumber;
        
        private String organizationName;
        private String licenseNumber;
        private String address;
        private String city;
        private String state;
        
        @Pattern(regexp = "^[0-9]{6}$", message = "PIN code must be 6 digits")
        private String pinCode;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSummaryDTO {
        private Long id;
        private String username;
        private String fullName;
        private User.UserRole role;
        private String organizationName;
        private Boolean isActive;
        private Boolean isVerified;
        private LocalDateTime createdAt;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JwtAuthenticationResponse {
        private String token;
        private String type = "Bearer";
        private UserResponseDTO user;
        private Long expiresIn;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PasswordChangeDTO {
        
        @NotBlank(message = "Current password is required")
        private String currentPassword;
        
        @NotBlank(message = "New password is required")
        @Size(min = 8, message = "New password must be at least 8 characters long")
        private String newPassword;
        
        @NotBlank(message = "Password confirmation is required")
        private String confirmPassword;
    }
}
