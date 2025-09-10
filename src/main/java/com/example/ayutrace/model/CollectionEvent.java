package com.example.ayutrace.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "collection_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CollectionEvent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "collection_id", unique = true, nullable = false)
    private String collectionId;
    
    @Column(name = "herb_name", nullable = false)
    private String herbName;
    
    @Column(name = "scientific_name")
    private String scientificName;
    
    @Column(name = "quantity_kg", nullable = false, precision = 10, scale = 3)
    private BigDecimal quantityKg;
    
    @Column(name = "collection_location", nullable = false)
    private String collectionLocation;
    
    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;
    
    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;
    
    @Column(name = "collection_date", nullable = false)
    private LocalDateTime collectionDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "collection_method")
    private CollectionMethod collectionMethod;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "season")
    private Season season;
    
    @Column(name = "weather_conditions")
    private String weatherConditions;
    
    @Column(name = "soil_type")
    private String soilType;
    
    @Column(name = "altitude_meters")
    private Integer altitudeMeters;
    
    @Column(name = "collection_time")
    private String collectionTime; // Morning/Afternoon/Evening
    
    @Column(name = "plant_part_used")
    private String plantPartUsed; // Root/Leaf/Stem/Flower/Fruit/Seed
    
    @Column(name = "harvest_maturity")
    private String harvestMaturity; // Young/Mature/Overripe
    
    @Column(name = "storage_conditions")
    private String storageConditions;
    
    @Column(name = "additional_notes", columnDefinition = "TEXT")
    private String additionalNotes;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CollectionStatus status = CollectionStatus.COLLECTED;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collector_id", nullable = false)
    private User collector;
    
    @OneToMany(mappedBy = "collectionEvent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProcessingStep> processingSteps;
    
    @OneToMany(mappedBy = "collectionEvent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QualityTest> qualityTests;
    
    // Enums
    public enum CollectionMethod {
        HAND_PICKED,
        CUTTING_TOOL,
        DIGGING,
        SHAKING,
        MECHANICAL
    }
    
    public enum Season {
        SPRING,
        SUMMER,
        MONSOON,
        WINTER
    }
    
    public enum CollectionStatus {
        COLLECTED,
        IN_STORAGE,
        SENT_FOR_PROCESSING,
        PROCESSED,
        TESTED,
        APPROVED,
        REJECTED,
        RECALLED
    }
}
