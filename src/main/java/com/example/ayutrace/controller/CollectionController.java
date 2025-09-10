package com.example.ayutrace.controller;

import com.example.ayutrace.dto.CollectionDTO;
import com.example.ayutrace.model.CollectionEvent;
import com.example.ayutrace.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collections")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001", "http://localhost:5173"})
public class CollectionController {
    
    @Autowired
    private CollectionService collectionService;
    
    /**
     * Get all collections (basic endpoint for frontend)
     */
    @GetMapping
    @PreAuthorize("hasRole('FARMER') or hasRole('PROCESSOR') or hasRole('LAB_TECHNICIAN') or hasRole('REGULATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<CollectionDTO.CollectionSummaryDTO>> getCollections() {
        try {
            List<CollectionDTO.CollectionSummaryDTO> collections = collectionService.getAllCollectionsAsList();
            return ResponseEntity.ok(collections);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Create a new collection event
     */
    @PostMapping
    @PreAuthorize("hasRole('FARMER')")
    public ResponseEntity<CollectionDTO.CollectionResponseDTO> createCollection(
            @Valid @RequestBody CollectionDTO.CollectionRequestDTO request) {
        try {
            CollectionDTO.CollectionResponseDTO response = collectionService.createCollection(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get collection by ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('FARMER') or hasRole('PROCESSOR') or hasRole('LAB_TECHNICIAN') or hasRole('REGULATOR') or hasRole('ADMIN')")
    public ResponseEntity<CollectionDTO.CollectionResponseDTO> getCollectionById(@PathVariable Long id) {
        try {
            CollectionDTO.CollectionResponseDTO response = collectionService.getCollectionById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get collection by collection ID
     */
    @GetMapping("/by-collection-id/{collectionId}")
    @PreAuthorize("hasRole('FARMER') or hasRole('PROCESSOR') or hasRole('LAB_TECHNICIAN') or hasRole('REGULATOR') or hasRole('ADMIN')")
    public ResponseEntity<CollectionDTO.CollectionResponseDTO> getCollectionByCollectionId(@PathVariable String collectionId) {
        try {
            CollectionDTO.CollectionResponseDTO response = collectionService.getCollectionByCollectionId(collectionId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get collections for current user
     */
    @GetMapping("/my-collections")
    @PreAuthorize("hasRole('FARMER')")
    public ResponseEntity<Page<CollectionDTO.CollectionSummaryDTO>> getMyCollections(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy) {
        try {
            Page<CollectionDTO.CollectionSummaryDTO> collections = 
                collectionService.getMyCollections(page, size, sortBy);
            return ResponseEntity.ok(collections);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get all collections (for admin/regulator)
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('REGULATOR')")
    public ResponseEntity<Page<CollectionDTO.CollectionSummaryDTO>> getAllCollections(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy) {
        try {
            Page<CollectionDTO.CollectionSummaryDTO> collections = 
                collectionService.getAllCollections(page, size, sortBy);
            return ResponseEntity.ok(collections);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Search collections
     */
    @GetMapping("/search")
    @PreAuthorize("hasRole('FARMER') or hasRole('PROCESSOR') or hasRole('LAB_TECHNICIAN') or hasRole('REGULATOR') or hasRole('ADMIN')")
    public ResponseEntity<Page<CollectionDTO.CollectionSummaryDTO>> searchCollections(
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<CollectionDTO.CollectionSummaryDTO> collections = 
                collectionService.searchCollections(searchTerm, page, size);
            return ResponseEntity.ok(collections);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get collections by herb name
     */
    @GetMapping("/by-herb/{herbName}")
    @PreAuthorize("hasRole('FARMER') or hasRole('PROCESSOR') or hasRole('LAB_TECHNICIAN') or hasRole('REGULATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<CollectionDTO.CollectionSummaryDTO>> getCollectionsByHerb(@PathVariable String herbName) {
        try {
            List<CollectionDTO.CollectionSummaryDTO> collections = 
                collectionService.getCollectionsByHerb(herbName);
            return ResponseEntity.ok(collections);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get collections by location
     */
    @GetMapping("/by-location/{location}")
    @PreAuthorize("hasRole('FARMER') or hasRole('PROCESSOR') or hasRole('LAB_TECHNICIAN') or hasRole('REGULATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<CollectionDTO.CollectionSummaryDTO>> getCollectionsByLocation(@PathVariable String location) {
        try {
            List<CollectionDTO.CollectionSummaryDTO> collections = 
                collectionService.getCollectionsByLocation(location);
            return ResponseEntity.ok(collections);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get collections by status
     */
    @GetMapping("/by-status/{status}")
    @PreAuthorize("hasRole('PROCESSOR') or hasRole('LAB_TECHNICIAN') or hasRole('REGULATOR') or hasRole('ADMIN')")
    public ResponseEntity<Page<CollectionDTO.CollectionSummaryDTO>> getCollectionsByStatus(
            @PathVariable CollectionEvent.CollectionStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<CollectionDTO.CollectionSummaryDTO> collections = 
                collectionService.getCollectionsByStatus(status, page, size);
            return ResponseEntity.ok(collections);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Update collection status
     */
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('FARMER') or hasRole('PROCESSOR') or hasRole('REGULATOR') or hasRole('ADMIN')")
    public ResponseEntity<CollectionDTO.CollectionResponseDTO> updateCollectionStatus(
            @PathVariable Long id, 
            @RequestBody Map<String, String> statusUpdate) {
        try {
            CollectionEvent.CollectionStatus status = CollectionEvent.CollectionStatus.valueOf(
                statusUpdate.get("status"));
            CollectionDTO.CollectionResponseDTO response = 
                collectionService.updateCollectionStatus(id, status);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get collection statistics
     */
    @GetMapping("/statistics")
    @PreAuthorize("hasRole('FARMER') or hasRole('REGULATOR') or hasRole('ADMIN')")
    public ResponseEntity<CollectionService.CollectionStatistics> getCollectionStatistics(
            @RequestParam(required = false) LocalDateTime startDate) {
        try {
            CollectionService.CollectionStatistics stats = 
                collectionService.getCollectionStatistics(startDate);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get distinct herb names
     */
    @GetMapping("/herbs")
    @PreAuthorize("hasRole('FARMER') or hasRole('PROCESSOR') or hasRole('LAB_TECHNICIAN') or hasRole('REGULATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<String>> getDistinctHerbNames() {
        try {
            List<String> herbNames = collectionService.getDistinctHerbNames();
            return ResponseEntity.ok(herbNames);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get distinct collection locations
     */
    @GetMapping("/locations")
    @PreAuthorize("hasRole('FARMER') or hasRole('PROCESSOR') or hasRole('LAB_TECHNICIAN') or hasRole('REGULATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<String>> getDistinctLocations() {
        try {
            List<String> locations = collectionService.getDistinctLocations();
            return ResponseEntity.ok(locations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get recent collections
     */
    @GetMapping("/recent")
    @PreAuthorize("hasRole('FARMER') or hasRole('PROCESSOR') or hasRole('LAB_TECHNICIAN') or hasRole('REGULATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<CollectionDTO.CollectionSummaryDTO>> getRecentCollections(
            @RequestParam(defaultValue = "10") int limit) {
        try {
            List<CollectionDTO.CollectionSummaryDTO> collections = 
                collectionService.getRecentCollections(limit);
            return ResponseEntity.ok(collections);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Delete collection (soft delete)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FARMER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCollection(@PathVariable Long id) {
        try {
            collectionService.deleteCollection(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Dashboard endpoint for collector dashboard matching frontend
     */
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('FARMER') or hasRole('ADMIN') or hasRole('REGULATOR')")
    public ResponseEntity<Map<String, Object>> getDashboardData() {
        try {
            CollectionService.CollectionStatistics stats = collectionService.getCollectionStatistics(null);
            List<CollectionDTO.CollectionSummaryDTO> recentCollections = collectionService.getRecentCollections(5);
            
            Map<String, Object> dashboardData = Map.of(
                "statistics", stats,
                "recentCollections", recentCollections,
                "herbNames", collectionService.getDistinctHerbNames(),
                "locations", collectionService.getDistinctLocations()
            );
            
            return ResponseEntity.ok(dashboardData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
