package com.example.ayutrace.dto;

import com.example.ayutrace.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ProvenanceDTO {
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CompleteProvenanceDTO {
        // Product Batch Information
        private String batchId;
        private String qrCode;
        private String productName;
        private String brandName;
        private String manufacturerName;
        private ProductBatch.ProductForm productForm;
        private String unitSize;
        private LocalDate manufacturingDate;
        private LocalDate expiryDate;
        private ProductBatch.BatchStatus status;
        
        // Collection Events
        private List<CollectionEventInfo> collectionEvents;
        
        // Processing Steps
        private List<ProcessingStepInfo> processingSteps;
        
        // Quality Tests
        private List<QualityTestInfo> qualityTests;
        
        // Blockchain Verification
        private String blockchainHash;
        private Boolean blockchainVerified;
        private BigDecimal traceabilityScore;
        private BigDecimal sustainabilityScore;
        
        // Regulatory Information
        private String ayushLicense;
        private String gmpCertificate;
        private String organicCertification;
        
        // Consumer Information
        private String dosageInstructions;
        private String storageInstructions;
        private String sideEffects;
        private String contraindications;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CollectionEventInfo {
        private String collectionId;
        private String herbName;
        private String scientificName;
        private BigDecimal quantityKg;
        private String collectionLocation;
        private BigDecimal latitude;
        private BigDecimal longitude;
        private LocalDateTime collectionDate;
        private CollectionEvent.Season season;
        private CollectionEvent.CollectionMethod collectionMethod;
        private String collectorName;
        private String collectorOrganization;
        private String weatherConditions;
        private String soilType;
        private Integer altitudeMeters;
        private String plantPartUsed;
        private String harvestMaturity;
        private CollectionEvent.CollectionStatus status;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProcessingStepInfo {
        private String processingId;
        private ProcessingStep.ProcessingType stepType;
        private String stepName;
        private BigDecimal inputQuantityKg;
        private BigDecimal outputQuantityKg;
        private LocalDateTime processingDate;
        private Integer temperatureCelsius;
        private Integer humidityPercentage;
        private Integer processingDurationHours;
        private String equipmentUsed;
        private String batchNumber;
        private BigDecimal yieldPercentage;
        private String qualityGrade;
        private String processorName;
        private String processorOrganization;
        private ProcessingStep.ProcessingStatus status;
        private String complianceStandards;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QualityTestInfo {
        private String testId;
        private QualityTest.TestType testType;
        private String testName;
        private LocalDateTime testDate;
        private String labName;
        private String labAccreditation;
        private QualityTest.TestResult testResult;
        private String gradeAssigned;
        private BigDecimal moistureContent;
        private BigDecimal ashContent;
        private BigDecimal authenticityScore;
        private Boolean adulterationDetected;
        private String testerName;
        private String certificateNumber;
        private String complianceStandards;
        private Integer shelfLifeMonths;
        
        // Critical test results for consumer display
        private Boolean pesticidesFree;
        private Boolean heavyMetalsSafe;
        private Boolean microbiologicallySafe;
        private Boolean authenticSpecies;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TraceabilityTimelineDTO {
        private String eventType; // COLLECTION, PROCESSING, TESTING, PACKAGING
        private String eventId;
        private String description;
        private LocalDateTime timestamp;
        private String location;
        private String responsiblePerson;
        private String status;
        private String details;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QRCodeProvenanceDTO {
        // Minimal information for QR code scanning
        private String batchId;
        private String productName;
        private String brandName;
        private LocalDate manufacturingDate;
        private LocalDate expiryDate;
        private String manufacturerName;
        private List<String> sourceLocations;
        private List<String> certifications;
        private BigDecimal traceabilityScore;
        private String provenanceUrl; // Link to detailed provenance
        private String verificationStatus;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConsumerProvenanceDTO {
        // Consumer-friendly provenance information
        private ProductInfo product;
        private OriginInfo origin;
        private QualityInfo quality;
        private SustainabilityInfo sustainability;
        private String verificationMessage;
        private List<CertificationInfo> certifications;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductInfo {
        private String name;
        private String brand;
        private String manufacturer;
        private LocalDate manufacturedOn;
        private LocalDate expiresOn;
        private String form;
        private String ingredients;
        private String dosageInstructions;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OriginInfo {
        private String primaryLocation;
        private List<String> allLocations;
        private String season;
        private String harvestMethod;
        private List<String> farmerNames;
        private String environmentalConditions;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QualityInfo {
        private String overallGrade;
        private String purity;
        private Boolean pesticideFree;
        private Boolean authenticSpecies;
        private Boolean microbiologicallySafe;
        private String qualityAssurance;
        private List<String> testingLabs;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SustainabilityInfo {
        private BigDecimal sustainabilityScore;
        private Boolean organicCertified;
        private Boolean fairTradeCertified;
        private String environmentalImpact;
        private String socialImpact;
        private List<String> sustainabilityPractices;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CertificationInfo {
        private String type;
        private String number;
        private String issuedBy;
        private LocalDate issuedDate;
        private LocalDate validUntil;
        private String status;
    }
}
