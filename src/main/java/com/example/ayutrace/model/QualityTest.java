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
@Table(name = "quality_tests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class QualityTest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "test_id", unique = true, nullable = false)
    private String testId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "test_type", nullable = false)
    private TestType testType;
    
    @Column(name = "test_name", nullable = false)
    private String testName;
    
    @Column(name = "test_date", nullable = false)
    private LocalDateTime testDate;
    
    @Column(name = "lab_name", nullable = false)
    private String labName;
    
    @Column(name = "lab_accreditation")
    private String labAccreditation;
    
    @Column(name = "test_method")
    private String testMethod;
    
    @Column(name = "sample_quantity_grams", precision = 8, scale = 3)
    private BigDecimal sampleQuantityGrams;
    
    // Physical Tests
    @Column(name = "moisture_content", precision = 5, scale = 2)
    private BigDecimal moistureContent;
    
    @Column(name = "ash_content", precision = 5, scale = 2)
    private BigDecimal ashContent;
    
    @Column(name = "extractive_value", precision = 5, scale = 2)
    private BigDecimal extractiveValue;
    
    @Column(name = "foreign_matter", precision = 5, scale = 2)
    private BigDecimal foreignMatter;
    
    // Chemical Tests
    @Column(name = "active_compounds", columnDefinition = "TEXT")
    private String activeCompounds; // JSON format
    
    @Column(name = "heavy_metals", columnDefinition = "TEXT")
    private String heavyMetals; // JSON format
    
    @Column(name = "pesticide_residues", columnDefinition = "TEXT")
    private String pesticideResidues; // JSON format
    
    @Column(name = "aflatoxins", precision = 8, scale = 3)
    private BigDecimal aflatoxins; // ppb
    
    @Column(name = "ph_value", precision = 4, scale = 2)
    private BigDecimal phValue;
    
    // Microbiological Tests
    @Column(name = "total_bacterial_count")
    private Integer totalBacterialCount;
    
    @Column(name = "yeast_mould_count")
    private Integer yeastMouldCount;
    
    @Column(name = "ecoli_presence")
    private Boolean ecoliPresence;
    
    @Column(name = "salmonella_presence")
    private Boolean salmonellaPresence;
    
    @Column(name = "staphylococcus_presence")
    private Boolean staphylococcusPresence;
    
    // DNA Barcoding
    @Column(name = "dna_barcode")
    private String dnaBarcode;
    
    @Column(name = "species_confirmation")
    private String speciesConfirmation;
    
    @Column(name = "authenticity_score", precision = 5, scale = 2)
    private BigDecimal authenticityScore;
    
    @Column(name = "adulteration_detected")
    private Boolean adulterationDetected;
    
    @Column(name = "adulterants_found", columnDefinition = "TEXT")
    private String adulterantsFound;
    
    // Results and Compliance
    @Enumerated(EnumType.STRING)
    @Column(name = "test_result", nullable = false)
    private TestResult testResult;
    
    @Column(name = "compliance_standards")
    private String complianceStandards; // WHO, Pharmacopoeia, etc.
    
    @Column(name = "grade_assigned")
    private String gradeAssigned;
    
    @Column(name = "shelf_life_months")
    private Integer shelfLifeMonths;
    
    @Column(name = "storage_recommendations")
    private String storageRecommendations;
    
    @Column(name = "test_report_url")
    private String testReportUrl;
    
    @Column(name = "certificate_number")
    private String certificateNumber;
    
    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;
    
    @Column(name = "retest_required")
    private Boolean retestRequired = false;
    
    @Column(name = "retest_reason")
    private String retestReason;
    
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
    @JoinColumn(name = "tester_id", nullable = false)
    private User tester;
    
    // Enums
    public enum TestType {
        PHYSICAL,
        CHEMICAL,
        MICROBIOLOGICAL,
        DNA_BARCODING,
        HEAVY_METALS,
        PESTICIDE_RESIDUE,
        AFLATOXIN,
        COMPREHENSIVE
    }
    
    public enum TestResult {
        PASS,
        FAIL,
        CONDITIONAL_PASS,
        PENDING,
        RETEST_REQUIRED,
        INCONCLUSIVE
    }
}
