package com.example.ayutrace.dto;

import com.example.ayutrace.model.QualityTest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class QualityTestDTO {
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QualityTestRequestDTO {
        
        @NotNull(message = "Collection event ID is required")
        private Long collectionEventId;
        
        @NotNull(message = "Test type is required")
        private QualityTest.TestType testType;
        
        @NotBlank(message = "Test name is required")
        private String testName;
        
        @NotNull(message = "Test date is required")
        private LocalDateTime testDate;
        
        @NotBlank(message = "Lab name is required")
        private String labName;
        
        private String labAccreditation;
        private String testMethod;
        
        @DecimalMin(value = "0.001", message = "Sample quantity must be greater than 0")
        private BigDecimal sampleQuantityGrams;
        
        // Physical Test Results
        @DecimalMin(value = "0.0", message = "Moisture content must be non-negative")
        @DecimalMax(value = "100.0", message = "Moisture content cannot exceed 100%")
        private BigDecimal moistureContent;
        
        @DecimalMin(value = "0.0", message = "Ash content must be non-negative")
        @DecimalMax(value = "100.0", message = "Ash content cannot exceed 100%")
        private BigDecimal ashContent;
        
        @DecimalMin(value = "0.0", message = "Extractive value must be non-negative")
        private BigDecimal extractiveValue;
        
        @DecimalMin(value = "0.0", message = "Foreign matter must be non-negative")
        private BigDecimal foreignMatter;
        
        // Chemical Test Results
        private String activeCompounds; // JSON
        private String heavyMetals; // JSON
        private String pesticideResidues; // JSON
        
        @DecimalMin(value = "0.0", message = "Aflatoxins must be non-negative")
        private BigDecimal aflatoxins;
        
        @DecimalMin(value = "0.0", message = "pH value must be positive")
        @DecimalMax(value = "14.0", message = "pH value cannot exceed 14")
        private BigDecimal phValue;
        
        // Microbiological Test Results
        @Min(value = 0, message = "Bacterial count cannot be negative")
        private Integer totalBacterialCount;
        
        @Min(value = 0, message = "Yeast/mould count cannot be negative")
        private Integer yeastMouldCount;
        
        private Boolean ecoliPresence;
        private Boolean salmonellaPresence;
        private Boolean staphylococcusPresence;
        
        // DNA Barcoding Results
        private String dnaBarcode;
        private String speciesConfirmation;
        
        @DecimalMin(value = "0.0", message = "Authenticity score must be non-negative")
        @DecimalMax(value = "100.0", message = "Authenticity score cannot exceed 100%")
        private BigDecimal authenticityScore;
        
        private Boolean adulterationDetected;
        private String adulterantsFound;
        
        // Results and Compliance
        @NotNull(message = "Test result is required")
        private QualityTest.TestResult testResult;
        
        private String complianceStandards;
        private String gradeAssigned;
        
        @Min(value = 1, message = "Shelf life must be at least 1 month")
        private Integer shelfLifeMonths;
        
        private String storageRecommendations;
        private String testReportUrl;
        private String certificateNumber;
        private String remarks;
        private Boolean retestRequired;
        private String retestReason;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QualityTestResponseDTO {
        private Long id;
        private String testId;
        private QualityTest.TestType testType;
        private String testName;
        private LocalDateTime testDate;
        private String labName;
        private String labAccreditation;
        private String testMethod;
        private BigDecimal sampleQuantityGrams;
        
        // Physical Test Results
        private BigDecimal moistureContent;
        private BigDecimal ashContent;
        private BigDecimal extractiveValue;
        private BigDecimal foreignMatter;
        
        // Chemical Test Results
        private String activeCompounds;
        private String heavyMetals;
        private String pesticideResidues;
        private BigDecimal aflatoxins;
        private BigDecimal phValue;
        
        // Microbiological Test Results
        private Integer totalBacterialCount;
        private Integer yeastMouldCount;
        private Boolean ecoliPresence;
        private Boolean salmonellaPresence;
        private Boolean staphylococcusPresence;
        
        // DNA Barcoding Results
        private String dnaBarcode;
        private String speciesConfirmation;
        private BigDecimal authenticityScore;
        private Boolean adulterationDetected;
        private String adulterantsFound;
        
        // Results and Compliance
        private QualityTest.TestResult testResult;
        private String complianceStandards;
        private String gradeAssigned;
        private Integer shelfLifeMonths;
        private String storageRecommendations;
        private String testReportUrl;
        private String certificateNumber;
        private String remarks;
        private Boolean retestRequired;
        private String retestReason;
        
        // Related Information
        private String collectionId;
        private String herbName;
        private String testerName;
        private String testerOrganization;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String blockchainHash;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QualityTestSummaryDTO {
        private Long id;
        private String testId;
        private QualityTest.TestType testType;
        private String testName;
        private LocalDateTime testDate;
        private String labName;
        private QualityTest.TestResult testResult;
        private String gradeAssigned;
        private String collectionId;
        private String herbName;
        private LocalDateTime createdAt;
    }
}
