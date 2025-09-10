package com.example.ayutrace.dto;

import com.example.ayutrace.model.ProcessingStep;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProcessingDTO {
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProcessingRequestDTO {
        
        @NotNull(message = "Collection event ID is required")
        private Long collectionEventId;
        
        @NotNull(message = "Step type is required")
        private ProcessingStep.ProcessingType stepType;
        
        @NotBlank(message = "Step name is required")
        private String stepName;
        
        @NotNull(message = "Processing date is required")
        private LocalDateTime processingDate;
        
        @DecimalMin(value = "0.001", message = "Input quantity must be greater than 0")
        private BigDecimal inputQuantityKg;
        
        @DecimalMin(value = "0.001", message = "Output quantity must be greater than 0")
        private BigDecimal outputQuantityKg;
        
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        
        @Min(value = -50, message = "Temperature must be reasonable")
        @Max(value = 200, message = "Temperature must be reasonable")
        private Integer temperatureCelsius;
        
        @Min(value = 0, message = "Humidity cannot be negative")
        @Max(value = 100, message = "Humidity cannot exceed 100%")
        private Integer humidityPercentage;
        
        @Min(value = 0, message = "Duration cannot be negative")
        private Integer processingDurationHours;
        
        private String equipmentUsed;
        private String processingParameters;
        private String batchNumber;
        
        @DecimalMin(value = "0.0", message = "Yield percentage cannot be negative")
        @DecimalMax(value = "100.0", message = "Yield percentage cannot exceed 100%")
        private BigDecimal yieldPercentage;
        
        private String qualityGrade;
        
        @DecimalMin(value = "0.0", message = "Moisture content cannot be negative")
        @DecimalMax(value = "100.0", message = "Moisture content cannot exceed 100%")
        private BigDecimal moistureContentPercentage;
        
        private String colorDescription;
        private String aromaDescription;
        private String textureDescription;
        private String storageConditionsPostProcessing;
        private String packagingType;
        private String notes;
        private String complianceStandards;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProcessingResponseDTO {
        private Long id;
        private String processingId;
        private ProcessingStep.ProcessingType stepType;
        private String stepName;
        private LocalDateTime processingDate;
        private BigDecimal inputQuantityKg;
        private BigDecimal outputQuantityKg;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Integer temperatureCelsius;
        private Integer humidityPercentage;
        private Integer processingDurationHours;
        private String equipmentUsed;
        private String processingParameters;
        private String batchNumber;
        private BigDecimal yieldPercentage;
        private String qualityGrade;
        private BigDecimal moistureContentPercentage;
        private String colorDescription;
        private String aromaDescription;
        private String textureDescription;
        private String storageConditionsPostProcessing;
        private String packagingType;
        private ProcessingStep.ProcessingStatus status;
        private String notes;
        private String complianceStandards;
        
        // Related information
        private String collectionId;
        private String herbName;
        private String processorName;
        private String processorOrganization;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String blockchainHash;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProcessingSummaryDTO {
        private Long id;
        private String processingId;
        private ProcessingStep.ProcessingType stepType;
        private String stepName;
        private LocalDateTime processingDate;
        private BigDecimal inputQuantityKg;
        private BigDecimal outputQuantityKg;
        private BigDecimal yieldPercentage;
        private ProcessingStep.ProcessingStatus status;
        private String collectionId;
        private String herbName;
        private String processorName;
        private LocalDateTime createdAt;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProcessingUpdateDTO {
        
        private ProcessingStep.ProcessingStatus status;
        private LocalDateTime endTime;
        
        @DecimalMin(value = "0.001", message = "Output quantity must be greater than 0")
        private BigDecimal outputQuantityKg;
        
        @DecimalMin(value = "0.0", message = "Yield percentage cannot be negative")
        @DecimalMax(value = "100.0", message = "Yield percentage cannot exceed 100%")
        private BigDecimal yieldPercentage;
        
        private String qualityGrade;
        private String colorDescription;
        private String aromaDescription;
        private String textureDescription;
        private String storageConditionsPostProcessing;
        private String packagingType;
        private String notes;
    }
}
