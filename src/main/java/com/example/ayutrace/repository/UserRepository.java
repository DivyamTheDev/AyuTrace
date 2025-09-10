package com.example.ayutrace.repository;

import com.example.ayutrace.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Authentication queries
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    
    // Verification queries
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsernameAndIdNot(String username, Long id);
    boolean existsByEmailAndIdNot(String email, Long id);
    
    // Role-based queries
    List<User> findByRole(User.UserRole role);
    Page<User> findByRole(User.UserRole role, Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE u.role IN :roles")
    List<User> findByRoles(@Param("roles") List<User.UserRole> roles);
    
    // Status queries
    List<User> findByIsActiveTrue();
    List<User> findByIsActiveFalse();
    List<User> findByIsVerifiedTrue();
    List<User> findByIsVerifiedFalse();
    
    @Query("SELECT u FROM User u WHERE u.role = :role AND u.isActive = true AND u.isVerified = true")
    List<User> findActiveVerifiedUsersByRole(@Param("role") User.UserRole role);
    
    // Organization queries
    List<User> findByOrganizationName(String organizationName);
    List<User> findByLicenseNumber(String licenseNumber);
    
    @Query("SELECT DISTINCT u.organizationName FROM User u WHERE u.organizationName IS NOT NULL ORDER BY u.organizationName")
    List<String> findDistinctOrganizations();
    
    // Location-based queries
    List<User> findByStateAndRole(String state, User.UserRole role);
    List<User> findByCityAndRole(String city, User.UserRole role);
    
    @Query("SELECT u FROM User u WHERE u.role = :role AND u.state = :state AND u.isActive = true")
    List<User> findActiveUsersByRoleAndState(@Param("role") User.UserRole role, @Param("state") String state);
    
    // Search queries
    @Query("SELECT u FROM User u WHERE " +
           "(LOWER(u.fullName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.organizationName) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "AND u.isActive = true")
    Page<User> searchActiveUsers(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE " +
           "u.role = :role AND " +
           "(LOWER(u.fullName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.organizationName) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "AND u.isActive = true")
    List<User> searchUsersByRoleAndName(@Param("role") User.UserRole role, @Param("search") String search);
    
    // Statistics queries
    @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
    Long countByRole(@Param("role") User.UserRole role);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role AND u.isActive = true")
    Long countActiveByRole(@Param("role") User.UserRole role);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.createdAt >= :startDate")
    Long countUsersCreatedAfter(@Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT u.role, COUNT(u) FROM User u WHERE u.isActive = true GROUP BY u.role")
    List<Object[]> countActiveUsersByRole();
    
    @Query("SELECT u.state, COUNT(u) FROM User u WHERE u.role = :role AND u.state IS NOT NULL GROUP BY u.state")
    List<Object[]> countUsersByRoleAndState(@Param("role") User.UserRole role);
    
    // Recent activity queries
    @Query("SELECT u FROM User u WHERE u.createdAt >= :since ORDER BY u.createdAt DESC")
    List<User> findRecentlyRegistered(@Param("since") LocalDateTime since);
    
    @Query("SELECT u FROM User u WHERE u.updatedAt >= :since AND u.updatedAt > u.createdAt ORDER BY u.updatedAt DESC")
    List<User> findRecentlyUpdated(@Param("since") LocalDateTime since);
    
    // Farmers with collection events
    @Query("SELECT DISTINCT u FROM User u JOIN u.collectionEvents c WHERE u.role = 'FARMER' AND c.createdAt >= :since")
    List<User> findActiveFarmers(@Param("since") LocalDateTime since);
    
    // Lab technicians with quality tests
    @Query("SELECT DISTINCT u FROM User u JOIN u.qualityTests q WHERE u.role = 'LAB_TECHNICIAN' AND q.createdAt >= :since")
    List<User> findActiveLabTechnicians(@Param("since") LocalDateTime since);
    
    // Processors with processing steps
    @Query("SELECT DISTINCT u FROM User u JOIN u.processingingSteps p WHERE u.role = 'PROCESSOR' AND p.createdAt >= :since")
    List<User> findActiveProcessors(@Param("since") LocalDateTime since);
}
