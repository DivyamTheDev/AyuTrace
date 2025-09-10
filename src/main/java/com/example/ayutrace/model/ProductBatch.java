package com.example.ayutrace.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "product_batches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductBatch {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "batch_id", unique = true, nullable = false)
    private String batchId;
    
    @Column(name = "qr_code", unique = true, nullable = false)
    private String qrCode;
    
    @Column(name = "product_name", nullable = false)
    private String productName;
    
    @Column(name = "brand_name")
    private String brandName;
    
    @Column(name = "manufacturer_name", nullable = false)
    private String manufacturerName;
    
    @Column(name = "manufacturer_license")
    private String manufacturerLicense;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "product_form", nullable = false)
    private ProductForm productForm;
    
    @Column(name = "total_quantity_kg", precision = 10, scale = 3)
    private BigDecimal totalQuantityKg;
    
    @Column(name = "unit_size")
    private String unitSize; // e.g., "100g", "250ml", "50 capsules"
    
    @Column(name = "units_produced")
    private Integer unitsProduced;
    
    @Column(name = "manufacturing_date", nullable = false)
    private LocalDate manufacturingDate;
    
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;
    
    @Column(name = "shelf_life_months")
    private Integer shelfLifeMonths;
    
    @Column(name = "batch_size")
    private String batchSize;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BatchStatus status = BatchStatus.PRODUCED;
    
    // Product Specifications
    @Column(name = "ingredients", columnDefinition = "TEXT")
    private String ingredients; // JSON format
    
    @Column(name = "composition", columnDefinition = "TEXT")
    private String composition; // Percentage of each herb
    
    @Column(name = "dosage_instructions", columnDefinition = "TEXT")
    private String dosageInstructions;
    
    @Column(name = "contraindications", columnDefinition = "TEXT")
    private String contraindications;
    
    @Column(name = "side_effects", columnDefinition = "TEXT")
    private String sideEffects;
    
    @Column(name = "storage_instructions")
    private String storageInstructions;
    
    // Regulatory Information
    @Column(name = "regulatory_approvals", columnDefinition = "TEXT")
    private String regulatoryApprovals; // JSON format
    
    @Column(name = "ayush_license")
    private String ayushLicense;
    
    @Column(name = "gmp_certificate")
    private String gmpCertificate;
    
    @Column(name = "iso_certification")
    private String isoCertification;
    
    @Column(name = "organic_certification")
    private String organicCertification;
    
    // Distribution
    @Column(name = "distributor_info", columnDefinition = "TEXT")
    private String distributorInfo; // JSON format
    
    @Column(name = "retail_price", precision = 10, scale = 2)
    private BigDecimal retailPrice;
    
    @Column(name = "mrp", precision = 10, scale = 2)
    private BigDecimal mrp;
    
    // Packaging Information
    @Column(name = "packaging_material")
    private String packagingMaterial;
    
    @Column(name = "label_information", columnDefinition = "TEXT")
    private String labelInformation;
    
    @Column(name = "barcode")
    private String barcode; // Traditional barcode
    
    // Quality & Testing
    @Column(name = "final_quality_grade")
    private String finalQualityGrade;
    
    @Column(name = "batch_testing_completed")
    private Boolean batchTestingCompleted = false;
    
    @Column(name = "release_approved")
    private Boolean releaseApproved = false;
    
    @Column(name = "approved_by")
    private String approvedBy;
    
    @Column(name = "approval_date")
    private LocalDateTime approvalDate;
    
    // Traceability
    @Column(name = "blockchain_hash")
    private String blockchainHash;
    
    @Column(name = "blockchain_verified")
    private Boolean blockchainVerified = false;
    
    @Column(name = "traceability_score", precision = 5, scale = 2)
    private BigDecimal traceabilityScore;
    
    @Column(name = "sustainability_score", precision = 5, scale = 2)
    private BigDecimal sustainabilityScore;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relationships
    @ManyToMany
    @JoinTable(
        name = "batch_collection_events",
        joinColumns = @JoinColumn(name = "batch_id"),
        inverseJoinColumns = @JoinColumn(name = "collection_event_id")
    )
    private List<CollectionEvent> sourceCollections;
    
    @ManyToMany
    @JoinTable(
        name = "batch_processing_steps",
        joinColumns = @JoinColumn(name = "batch_id"),
        inverseJoinColumns = @JoinColumn(name = "processing_step_id")
    )
    private List<ProcessingStep> processingSteps;
    
    @ManyToMany
    @JoinTable(
        name = "batch_quality_tests",
        joinColumns = @JoinColumn(name = "batch_id"),
        inverseJoinColumns = @JoinColumn(name = "quality_test_id")
    )
    private List<QualityTest> qualityTests;
    
    // Enums
    public enum ProductForm {
        POWDER,
        CAPSULE,
        TABLET,
        LIQUID_EXTRACT,
        OIL,
        CREAM,
        OINTMENT,
        SYRUP,
        TEA_BAG,
        DRIED_HERB,
        ESSENTIAL_OIL,
        TINCTURE
    }
    
    public enum BatchStatus {
        PRODUCED,
        TESTED,
        APPROVED,
        RELEASED,
        DISTRIBUTED,
        RECALLED,
        EXPIRED,
        REJECTED
    }
}
