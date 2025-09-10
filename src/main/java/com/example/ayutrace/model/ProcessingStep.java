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

@Entity
@Table(name = "processing_steps")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProcessingStep {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "processing_id", unique = true, nullable = false)
    private String processingId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "step_type", nullable = false)
    private ProcessingType stepType;
    
    @Column(name = "step_name", nullable = false)
    private String stepName;
    
    @Column(name = "input_quantity_kg", precision = 10, scale = 3)
    private BigDecimal inputQuantityKg;
    
    @Column(name = "output_quantity_kg", precision = 10, scale = 3)
    private BigDecimal outputQuantityKg;
    
    @Column(name = "processing_date", nullable = false)
    private LocalDateTime processingDate;
    
    @Column(name = "start_time")
    private LocalDateTime startTime;
    
    @Column(name = "end_time")
    private LocalDateTime endTime;
    
    @Column(name = "temperature_celsius")
    private Integer temperatureCelsius;
    
    @Column(name = "humidity_percentage")
    private Integer humidityPercentage;
    
    @Column(name = "processing_duration_hours")
    private Integer processingDurationHours;
    
    @Column(name = "equipment_used")
    private String equipmentUsed;
    
    @Column(name = "processing_parameters", columnDefinition = "TEXT")
    private String processingParameters;
    
    @Column(name = "batch_number")
    private String batchNumber;
    
    @Column(name = "yield_percentage", precision = 5, scale = 2)
    private BigDecimal yieldPercentage;
    
    @Column(name = "quality_grade")
    private String qualityGrade;
    
    @Column(name = "moisture_content_percentage", precision = 5, scale = 2)
    private BigDecimal moistureContentPercentage;
    
    @Column(name = "color_description")
    private String colorDescription;
    
    @Column(name = "aroma_description")
    private String aromaDescription;
    
    @Column(name = "texture_description")
    private String textureDescription;
    
    @Column(name = "storage_conditions_post_processing")
    private String storageConditionsPostProcessing;
    
    @Column(name = "packaging_type")
    private String packagingType;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProcessingStatus status = ProcessingStatus.IN_PROGRESS;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "compliance_standards")
    private String complianceStandards;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_event_id", nullable = false)
    private CollectionEvent collectionEvent;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processor_id", nullable = false)
    private User processor;
    
    // Enums
    public enum ProcessingType {
        CLEANING,
        SORTING,
        WASHING,
        DRYING,
        GRINDING,
        SIEVING,
        EXTRACTION,
        DISTILLATION,
        FERMENTATION,
        STEAMING,
        ROASTING,
        PACKAGING,
        STORAGE
    }
    
    public enum ProcessingStatus {
        PENDING,
        IN_PROGRESS,
        COMPLETED,
        ON_HOLD,
        REJECTED,
        REWORK_REQUIRED
    }
}
