package com.example.ayutrace.service;

import com.example.ayutrace.dto.CollectionDTO;
import com.example.ayutrace.model.CollectionEvent;
import com.example.ayutrace.model.User;
import com.example.ayutrace.repository.CollectionEventRepository;
import com.example.ayutrace.repository.UserRepository;
import com.example.ayutrace.util.AyurTraceUtils;
import com.example.ayutrace.util.GeoValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CollectionService {
    
    private static final Logger logger = LoggerFactory.getLogger(CollectionService.class);
    
    @Autowired
    private CollectionEventRepository collectionRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AyurTraceUtils ayurTraceUtils;
    
    @Autowired
    private GeoValidator geoValidator;
    
    /**
     * Create a new collection event
     */
    public CollectionDTO.CollectionResponseDTO createCollection(CollectionDTO.CollectionRequestDTO request) {
        try {
            // Get current user
            User currentUser = getCurrentUser();
            if (currentUser.getRole() != User.UserRole.FARMER) {
                throw new RuntimeException("Only farmers can create collection events");
            }
            
            // Validate coordinates
            if (!geoValidator.isValidIndianCoordinates(request.getLatitude(), request.getLongitude())) {
                throw new RuntimeException("Invalid coordinates for Indian location");
            }
            
            // Validate herb location
            GeoValidator.ValidationResult locationValidation = geoValidator.validateCollectionLocation(
                request.getLatitude(), request.getLongitude(), request.getHerbName(), null);
            
            if (!locationValidation.isValid()) {
                throw new RuntimeException("Location validation failed: " + 
                    String.join(", ", locationValidation.getErrors()));
            }
            
            // Create collection event
            CollectionEvent collection = new CollectionEvent();
            collection.setCollectionId(ayurTraceUtils.generateCollectionId());
            collection.setHerbName(ayurTraceUtils.sanitizeString(request.getHerbName()));
            collection.setScientificName(ayurTraceUtils.sanitizeString(request.getScientificName()));
            collection.setQuantityKg(request.getQuantityKg());
            collection.setCollectionLocation(ayurTraceUtils.sanitizeString(request.getCollectionLocation()));
            collection.setLatitude(request.getLatitude());
            collection.setLongitude(request.getLongitude());
            collection.setCollectionDate(request.getCollectionDate());
            collection.setCollectionMethod(request.getCollectionMethod());
            collection.setSeason(request.getSeason());
            collection.setWeatherConditions(ayurTraceUtils.sanitizeString(request.getWeatherConditions()));
            collection.setSoilType(ayurTraceUtils.sanitizeString(request.getSoilType()));
            collection.setAltitudeMeters(request.getAltitudeMeters());
            collection.setCollectionTime(request.getCollectionTime());
            collection.setPlantPartUsed(request.getPlantPartUsed());
            collection.setHarvestMaturity(request.getHarvestMaturity());
            collection.setStorageConditions(ayurTraceUtils.sanitizeString(request.getStorageConditions()));
            collection.setAdditionalNotes(ayurTraceUtils.sanitizeString(request.getAdditionalNotes()));
            collection.setCollector(currentUser);
            collection.setStatus(CollectionEvent.CollectionStatus.COLLECTED);
            
            // Save collection
            CollectionEvent savedCollection = collectionRepository.save(collection);
            logger.info("Collection created successfully: {}", savedCollection.getCollectionId());
            
            return mapToResponseDTO(savedCollection);
            
        } catch (Exception e) {
            logger.error("Error creating collection", e);
            throw new RuntimeException("Failed to create collection: " + e.getMessage());
        }
    }
    
    /**
     * Get collection by ID
     */
    public CollectionDTO.CollectionResponseDTO getCollectionById(Long id) {
        Optional<CollectionEvent> collection = collectionRepository.findById(id);
        if (collection.isEmpty()) {
            throw new RuntimeException("Collection not found");
        }
        return mapToResponseDTO(collection.get());
    }
    
    /**
     * Get collection by collection ID
     */
    public CollectionDTO.CollectionResponseDTO getCollectionByCollectionId(String collectionId) {
        Optional<CollectionEvent> collection = collectionRepository.findByCollectionId(collectionId);
        if (collection.isEmpty()) {
            throw new RuntimeException("Collection not found");
        }
        return mapToResponseDTO(collection.get());
    }
    
    /**
     * Get collections for current user
     */
    public Page<CollectionDTO.CollectionSummaryDTO> getMyCollections(int page, int size, String sortBy) {
        User currentUser = getCurrentUser();
        
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy != null ? sortBy : "collectionDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<CollectionEvent> collections = collectionRepository.findByCollector(currentUser, pageable);
        return collections.map(this::mapToSummaryDTO);
    }
    
    /**
     * Get all collections (for admin/regulator)
     */
    public Page<CollectionDTO.CollectionSummaryDTO> getAllCollections(int page, int size, String sortBy) {
        User currentUser = getCurrentUser();
        if (currentUser.getRole() != User.UserRole.ADMIN && currentUser.getRole() != User.UserRole.REGULATOR) {
            throw new RuntimeException("Unauthorized access");
        }
        
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy != null ? sortBy : "collectionDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<CollectionEvent> collections = collectionRepository.findAll(pageable);
        return collections.map(this::mapToSummaryDTO);
    }
    
    /**
     * Get all collections as a list (for basic frontend endpoint)
     */
    public List<CollectionDTO.CollectionSummaryDTO> getAllCollectionsAsList() {
        User currentUser = getCurrentUser();
        
        // For farmers, return only their collections
        if (currentUser.getRole() == User.UserRole.FARMER) {
            List<CollectionEvent> collections = collectionRepository.findByCollectorOrderByCollectionDateDesc(currentUser);
            return collections.stream()
                             .map(this::mapToSummaryDTO)
                             .collect(Collectors.toList());
        } else {
            // For other roles, return all collections (limited to recent ones)
            List<CollectionEvent> collections = collectionRepository.findTop50ByOrderByCollectionDateDesc();
            return collections.stream()
                             .map(this::mapToSummaryDTO)
                             .collect(Collectors.toList());
        }
    }
    
    /**
     * Search collections
     */
    public Page<CollectionDTO.CollectionSummaryDTO> searchCollections(String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "collectionDate"));
        Page<CollectionEvent> collections = collectionRepository.searchCollections(searchTerm, pageable);
        return collections.map(this::mapToSummaryDTO);
    }
    
    /**
     * Get collections by herb name
     */
    public List<CollectionDTO.CollectionSummaryDTO> getCollectionsByHerb(String herbName) {
        List<CollectionEvent> collections = collectionRepository.findByHerbNameIgnoreCase(herbName);
        return collections.stream()
                         .map(this::mapToSummaryDTO)
                         .collect(Collectors.toList());
    }
    
    /**
     * Get collections by location
     */
    public List<CollectionDTO.CollectionSummaryDTO> getCollectionsByLocation(String location) {
        List<CollectionEvent> collections = collectionRepository.findByCollectionLocationContainingIgnoreCase(location);
        return collections.stream()
                         .map(this::mapToSummaryDTO)
                         .collect(Collectors.toList());
    }
    
    /**
     * Get collections by status
     */
    public Page<CollectionDTO.CollectionSummaryDTO> getCollectionsByStatus(CollectionEvent.CollectionStatus status, 
                                                                           int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "collectionDate"));
        Page<CollectionEvent> collections = collectionRepository.findByStatus(status, pageable);
        return collections.map(this::mapToSummaryDTO);
    }
    
    /**
     * Update collection status
     */
    public CollectionDTO.CollectionResponseDTO updateCollectionStatus(Long id, CollectionEvent.CollectionStatus status) {
        Optional<CollectionEvent> optionalCollection = collectionRepository.findById(id);
        if (optionalCollection.isEmpty()) {
            throw new RuntimeException("Collection not found");
        }
        
        CollectionEvent collection = optionalCollection.get();
        
        // Check permissions
        User currentUser = getCurrentUser();
        if (!canUpdateCollection(currentUser, collection)) {
            throw new RuntimeException("Unauthorized to update this collection");
        }
        
        collection.setStatus(status);
        CollectionEvent updatedCollection = collectionRepository.save(collection);
        
        logger.info("Collection status updated: {} -> {}", collection.getCollectionId(), status);
        return mapToResponseDTO(updatedCollection);
    }
    
    /**
     * Get collection statistics
     */
    public CollectionStatistics getCollectionStatistics(LocalDateTime startDate) {
        if (startDate == null) {
            startDate = LocalDateTime.now().minusMonths(1);
        }
        
        CollectionStatistics stats = new CollectionStatistics();
        
        User currentUser = getCurrentUser();
        if (currentUser.getRole() == User.UserRole.FARMER) {
            // Farmer-specific statistics
            stats.setTotalCollections(collectionRepository.countByCollector(currentUser).intValue());
            stats.setTotalQuantity(collectionRepository.sumQuantityByCollector(currentUser));
            stats.setMyCollections(true);
        } else {
            // System-wide statistics
            List<Object[]> herbStats = collectionRepository.getHerbQuantityStatistics(startDate);
            List<Object[]> locationStats = collectionRepository.getLocationStatistics(startDate);
            List<Object[]> monthlyStats = collectionRepository.getMonthlyStatistics(startDate);
            
            stats.setHerbStatistics(herbStats);
            stats.setLocationStatistics(locationStats);
            stats.setMonthlyStatistics(monthlyStats);
            stats.setMyCollections(false);
        }
        
        return stats;
    }
    
    /**
     * Get distinct herb names
     */
    public List<String> getDistinctHerbNames() {
        return collectionRepository.findDistinctHerbNames();
    }
    
    /**
     * Get distinct collection locations
     */
    public List<String> getDistinctLocations() {
        return collectionRepository.findDistinctCollectionLocations();
    }
    
    /**
     * Get recent collections
     */
    public List<CollectionDTO.CollectionSummaryDTO> getRecentCollections(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        Page<CollectionEvent> collections = collectionRepository.findRecentCollections(pageable);
        return collections.stream()
                         .map(this::mapToSummaryDTO)
                         .collect(Collectors.toList());
    }
    
    /**
     * Delete collection (soft delete by changing status)
     */
    public void deleteCollection(Long id) {
        Optional<CollectionEvent> optionalCollection = collectionRepository.findById(id);
        if (optionalCollection.isEmpty()) {
            throw new RuntimeException("Collection not found");
        }
        
        CollectionEvent collection = optionalCollection.get();
        
        // Check permissions
        User currentUser = getCurrentUser();
        if (!canDeleteCollection(currentUser, collection)) {
            throw new RuntimeException("Unauthorized to delete this collection");
        }
        
        // Soft delete by changing status
        collection.setStatus(CollectionEvent.CollectionStatus.REJECTED);
        collectionRepository.save(collection);
        
        logger.info("Collection soft deleted: {}", collection.getCollectionId());
    }
    
    // Helper methods
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                           .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    private boolean canUpdateCollection(User user, CollectionEvent collection) {
        return user.getRole() == User.UserRole.ADMIN ||
               user.getRole() == User.UserRole.REGULATOR ||
               (user.getRole() == User.UserRole.FARMER && collection.getCollector().equals(user));
    }
    
    private boolean canDeleteCollection(User user, CollectionEvent collection) {
        return user.getRole() == User.UserRole.ADMIN ||
               (user.getRole() == User.UserRole.FARMER && collection.getCollector().equals(user) &&
                collection.getStatus() == CollectionEvent.CollectionStatus.COLLECTED);
    }
    
    private CollectionDTO.CollectionResponseDTO mapToResponseDTO(CollectionEvent collection) {
        CollectionDTO.CollectionResponseDTO dto = new CollectionDTO.CollectionResponseDTO();
        dto.setId(collection.getId());
        dto.setCollectionId(collection.getCollectionId());
        dto.setHerbName(collection.getHerbName());
        dto.setScientificName(collection.getScientificName());
        dto.setQuantityKg(collection.getQuantityKg());
        dto.setCollectionLocation(collection.getCollectionLocation());
        dto.setLatitude(collection.getLatitude());
        dto.setLongitude(collection.getLongitude());
        dto.setCollectionDate(collection.getCollectionDate());
        dto.setCollectionMethod(collection.getCollectionMethod());
        dto.setSeason(collection.getSeason());
        dto.setWeatherConditions(collection.getWeatherConditions());
        dto.setSoilType(collection.getSoilType());
        dto.setAltitudeMeters(collection.getAltitudeMeters());
        dto.setCollectionTime(collection.getCollectionTime());
        dto.setPlantPartUsed(collection.getPlantPartUsed());
        dto.setHarvestMaturity(collection.getHarvestMaturity());
        dto.setStorageConditions(collection.getStorageConditions());
        dto.setAdditionalNotes(collection.getAdditionalNotes());
        dto.setStatus(collection.getStatus());
        dto.setCollectorName(collection.getCollector().getFullName());
        dto.setCollectorOrganization(collection.getCollector().getOrganizationName());
        dto.setCreatedAt(collection.getCreatedAt());
        dto.setUpdatedAt(collection.getUpdatedAt());
        
        return dto;
    }
    
    private CollectionDTO.CollectionSummaryDTO mapToSummaryDTO(CollectionEvent collection) {
        CollectionDTO.CollectionSummaryDTO dto = new CollectionDTO.CollectionSummaryDTO();
        dto.setId(collection.getId());
        dto.setCollectionId(collection.getCollectionId());
        dto.setHerbName(collection.getHerbName());
        dto.setQuantityKg(collection.getQuantityKg());
        dto.setCollectionLocation(collection.getCollectionLocation());
        dto.setCollectionDate(collection.getCollectionDate());
        dto.setStatus(collection.getStatus());
        dto.setCollectorName(collection.getCollector().getFullName());
        dto.setCreatedAt(collection.getCreatedAt());
        
        return dto;
    }
    
    // Statistics DTO
    public static class CollectionStatistics {
        private int totalCollections;
        private java.math.BigDecimal totalQuantity;
        private boolean myCollections;
        private List<Object[]> herbStatistics;
        private List<Object[]> locationStatistics;
        private List<Object[]> monthlyStatistics;
        
        // Getters and setters
        public int getTotalCollections() { return totalCollections; }
        public void setTotalCollections(int totalCollections) { this.totalCollections = totalCollections; }
        
        public java.math.BigDecimal getTotalQuantity() { return totalQuantity; }
        public void setTotalQuantity(java.math.BigDecimal totalQuantity) { this.totalQuantity = totalQuantity; }
        
        public boolean isMyCollections() { return myCollections; }
        public void setMyCollections(boolean myCollections) { this.myCollections = myCollections; }
        
        public List<Object[]> getHerbStatistics() { return herbStatistics; }
        public void setHerbStatistics(List<Object[]> herbStatistics) { this.herbStatistics = herbStatistics; }
        
        public List<Object[]> getLocationStatistics() { return locationStatistics; }
        public void setLocationStatistics(List<Object[]> locationStatistics) { this.locationStatistics = locationStatistics; }
        
        public List<Object[]> getMonthlyStatistics() { return monthlyStatistics; }
        public void setMonthlyStatistics(List<Object[]> monthlyStatistics) { this.monthlyStatistics = monthlyStatistics; }
    }
}
