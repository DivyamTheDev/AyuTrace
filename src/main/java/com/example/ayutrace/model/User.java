package com.example.ayutrace.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(name = "full_name", nullable = false)
    private String fullName;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
    
    @Column(name = "organization_name")
    private String organizationName;
    
    @Column(name = "license_number")
    private String licenseNumber;
    
    private String address;
    
    private String city;
    
    private String state;
    
    @Column(name = "pin_code")
    private String pinCode;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relationships
    @OneToMany(mappedBy = "collector", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CollectionEvent> collectionEvents;
    
    @OneToMany(mappedBy = "processor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProcessingStep> processingingSteps;
    
    @OneToMany(mappedBy = "tester", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QualityTest> qualityTests;
    
    public enum UserRole {
        FARMER,
        PROCESSOR, 
        LAB_TECHNICIAN,
        REGULATOR,
        CONSUMER,
        ADMIN
    }
}
