package com.example.ayutrace.dto;

import com.example.ayutrace.model.CollectionEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CollectionDTO {
    
    public static class CollectionRequestDTO {
        
        @NotBlank(message = "Herb name is required")
        private String herbName;
        
        private String scientificName;
        
        @NotNull(message = "Quantity is required")
        @DecimalMin(value = "0.001", message = "Quantity must be greater than 0")
        private BigDecimal quantityKg;
        
        @NotBlank(message = "Collection location is required")
        private String collectionLocation;
        
        @DecimalMin(value = "-90.0", message = "Invalid latitude")
        @DecimalMax(value = "90.0", message = "Invalid latitude")
        private BigDecimal latitude;
        
        @DecimalMin(value = "-180.0", message = "Invalid longitude")
        @DecimalMax(value = "180.0", message = "Invalid longitude")
        private BigDecimal longitude;
        
        @NotNull(message = "Collection date is required")
        private LocalDateTime collectionDate;
        
        private CollectionEvent.CollectionMethod collectionMethod;
        private CollectionEvent.Season season;
        private String weatherConditions;
        private String soilType;
        private Integer altitudeMeters;
        private String collectionTime;
        private String plantPartUsed;
        private String harvestMaturity;
        private String storageConditions;
        private String additionalNotes;
        
        // Getters and Setters
        public String getHerbName() { return herbName; }
        public void setHerbName(String herbName) { this.herbName = herbName; }
        
        public String getScientificName() { return scientificName; }
        public void setScientificName(String scientificName) { this.scientificName = scientificName; }
        
        public BigDecimal getQuantityKg() { return quantityKg; }
        public void setQuantityKg(BigDecimal quantityKg) { this.quantityKg = quantityKg; }
        
        public String getCollectionLocation() { return collectionLocation; }
        public void setCollectionLocation(String collectionLocation) { this.collectionLocation = collectionLocation; }
        
        public BigDecimal getLatitude() { return latitude; }
        public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }
        
        public BigDecimal getLongitude() { return longitude; }
        public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }
        
        public LocalDateTime getCollectionDate() { return collectionDate; }
        public void setCollectionDate(LocalDateTime collectionDate) { this.collectionDate = collectionDate; }
        
        public CollectionEvent.CollectionMethod getCollectionMethod() { return collectionMethod; }
        public void setCollectionMethod(CollectionEvent.CollectionMethod collectionMethod) { this.collectionMethod = collectionMethod; }
        
        public CollectionEvent.Season getSeason() { return season; }
        public void setSeason(CollectionEvent.Season season) { this.season = season; }
        
        public String getWeatherConditions() { return weatherConditions; }
        public void setWeatherConditions(String weatherConditions) { this.weatherConditions = weatherConditions; }
        
        public String getSoilType() { return soilType; }
        public void setSoilType(String soilType) { this.soilType = soilType; }
        
        public Integer getAltitudeMeters() { return altitudeMeters; }
        public void setAltitudeMeters(Integer altitudeMeters) { this.altitudeMeters = altitudeMeters; }
        
        public String getCollectionTime() { return collectionTime; }
        public void setCollectionTime(String collectionTime) { this.collectionTime = collectionTime; }
        
        public String getPlantPartUsed() { return plantPartUsed; }
        public void setPlantPartUsed(String plantPartUsed) { this.plantPartUsed = plantPartUsed; }
        
        public String getHarvestMaturity() { return harvestMaturity; }
        public void setHarvestMaturity(String harvestMaturity) { this.harvestMaturity = harvestMaturity; }
        
        public String getStorageConditions() { return storageConditions; }
        public void setStorageConditions(String storageConditions) { this.storageConditions = storageConditions; }
        
        public String getAdditionalNotes() { return additionalNotes; }
        public void setAdditionalNotes(String additionalNotes) { this.additionalNotes = additionalNotes; }
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CollectionResponseDTO {
        private Long id;
        private String collectionId;
        private String herbName;
        private String scientificName;
        private BigDecimal quantityKg;
        private String collectionLocation;
        private BigDecimal latitude;
        private BigDecimal longitude;
        private LocalDateTime collectionDate;
        private CollectionEvent.CollectionMethod collectionMethod;
        private CollectionEvent.Season season;
        private String weatherConditions;
        private String soilType;
        private Integer altitudeMeters;
        private String collectionTime;
        private String plantPartUsed;
        private String harvestMaturity;
        private String storageConditions;
        private String additionalNotes;
        private CollectionEvent.CollectionStatus status;
        private String collectorName;
        private String collectorOrganization;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String blockchainHash;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CollectionSummaryDTO {
        private Long id;
        private String collectionId;
        private String herbName;
        private BigDecimal quantityKg;
        private String collectionLocation;
        private LocalDateTime collectionDate;
        private CollectionEvent.CollectionStatus status;
        private String collectorName;
        private LocalDateTime createdAt;
    }
}
