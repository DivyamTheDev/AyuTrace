package com.example.ayutrace.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GeoValidator {
    
    private static final Logger logger = LoggerFactory.getLogger(GeoValidator.class);
    
    // Indian states and their approximate coordinate bounds
    private static final Map<String, CoordinateBounds> INDIAN_STATES_BOUNDS = new HashMap<>();
    
    static {
        // Initialize state bounds (approximate coordinates)
        INDIAN_STATES_BOUNDS.put("KERALA", new CoordinateBounds(8.2, 12.8, 74.9, 77.4));
        INDIAN_STATES_BOUNDS.put("RAJASTHAN", new CoordinateBounds(23.0, 30.2, 69.5, 78.3));
        INDIAN_STATES_BOUNDS.put("UTTARAKHAND", new CoordinateBounds(28.4, 31.5, 77.6, 81.1));
        INDIAN_STATES_BOUNDS.put("GUJARAT", new CoordinateBounds(20.1, 24.7, 68.2, 74.5));
        INDIAN_STATES_BOUNDS.put("MAHARASHTRA", new CoordinateBounds(15.6, 22.0, 72.6, 80.9));
        INDIAN_STATES_BOUNDS.put("KARNATAKA", new CoordinateBounds(11.3, 18.5, 74.1, 78.6));
        INDIAN_STATES_BOUNDS.put("TAMIL_NADU", new CoordinateBounds(8.1, 13.6, 76.2, 80.4));
        INDIAN_STATES_BOUNDS.put("ANDHRA_PRADESH", new CoordinateBounds(12.6, 19.9, 76.8, 84.8));
        INDIAN_STATES_BOUNDS.put("TELANGANA", new CoordinateBounds(15.8, 19.9, 77.3, 81.8));
        INDIAN_STATES_BOUNDS.put("ODISHA", new CoordinateBounds(17.8, 22.6, 81.3, 87.5));
        INDIAN_STATES_BOUNDS.put("WEST_BENGAL", new CoordinateBounds(21.3, 27.2, 85.8, 89.9));
        INDIAN_STATES_BOUNDS.put("BIHAR", new CoordinateBounds(24.2, 27.5, 83.3, 88.4));
        INDIAN_STATES_BOUNDS.put("JHARKHAND", new CoordinateBounds(21.9, 25.3, 83.3, 87.9));
        INDIAN_STATES_BOUNDS.put("UTTAR_PRADESH", new CoordinateBounds(23.9, 30.4, 77.1, 84.6));
        INDIAN_STATES_BOUNDS.put("MADHYA_PRADESH", new CoordinateBounds(21.1, 26.9, 74.0, 82.8));
        INDIAN_STATES_BOUNDS.put("CHHATTISGARH", new CoordinateBounds(17.8, 24.1, 80.3, 84.4));
        INDIAN_STATES_BOUNDS.put("HARYANA", new CoordinateBounds(27.4, 30.9, 74.5, 77.7));
        INDIAN_STATES_BOUNDS.put("PUNJAB", new CoordinateBounds(29.5, 32.5, 73.9, 76.9));
        INDIAN_STATES_BOUNDS.put("HIMACHAL_PRADESH", new CoordinateBounds(30.4, 33.2, 75.5, 79.0));
        INDIAN_STATES_BOUNDS.put("JAMMU_KASHMIR", new CoordinateBounds(32.3, 37.1, 72.6, 80.3));
        INDIAN_STATES_BOUNDS.put("ASSAM", new CoordinateBounds(24.1, 28.0, 89.7, 96.0));
        INDIAN_STATES_BOUNDS.put("ARUNACHAL_PRADESH", new CoordinateBounds(26.6, 29.5, 91.6, 97.4));
        INDIAN_STATES_BOUNDS.put("NAGALAND", new CoordinateBounds(25.2, 27.0, 93.3, 95.8));
        INDIAN_STATES_BOUNDS.put("MANIPUR", new CoordinateBounds(23.8, 25.7, 93.0, 94.8));
        INDIAN_STATES_BOUNDS.put("MIZORAM", new CoordinateBounds(21.9, 24.5, 92.2, 93.7));
        INDIAN_STATES_BOUNDS.put("TRIPURA", new CoordinateBounds(22.9, 24.5, 91.1, 92.4));
        INDIAN_STATES_BOUNDS.put("MEGHALAYA", new CoordinateBounds(25.0, 26.1, 89.9, 92.8));
        INDIAN_STATES_BOUNDS.put("SIKKIM", new CoordinateBounds(27.0, 28.1, 88.0, 88.9));
        INDIAN_STATES_BOUNDS.put("GOA", new CoordinateBounds(15.0, 15.8, 73.7, 74.3));
    }
    
    // Known herb-growing regions in India
    private static final Map<String, List<String>> HERB_GROWING_REGIONS = new HashMap<>();
    
    static {
        HERB_GROWING_REGIONS.put("TURMERIC", Arrays.asList("TAMIL_NADU", "ANDHRA_PRADESH", "TELANGANA", "ODISHA", "KARNATAKA", "MAHARASHTRA"));
        HERB_GROWING_REGIONS.put("ASHWAGANDHA", Arrays.asList("MADHYA_PRADESH", "RAJASTHAN", "GUJARAT", "HARYANA", "UTTAR_PRADESH"));
        HERB_GROWING_REGIONS.put("TULSI", Arrays.asList("UTTAR_PRADESH", "BIHAR", "WEST_BENGAL", "ODISHA", "MADHYA_PRADESH", "RAJASTHAN"));
        HERB_GROWING_REGIONS.put("NEEM", Arrays.asList("GUJARAT", "RAJASTHAN", "MADHYA_PRADESH", "ANDHRA_PRADESH", "KARNATAKA", "TAMIL_NADU"));
        HERB_GROWING_REGIONS.put("BRAHMI", Arrays.asList("KERALA", "KARNATAKA", "TAMIL_NADU", "ANDHRA_PRADESH", "WEST_BENGAL"));
        HERB_GROWING_REGIONS.put("AMLA", Arrays.asList("UTTAR_PRADESH", "MADHYA_PRADESH", "RAJASTHAN", "GUJARAT", "HIMACHAL_PRADESH"));
        HERB_GROWING_REGIONS.put("GINGER", Arrays.asList("KERALA", "KARNATAKA", "ASSAM", "ARUNACHAL_PRADESH", "MEGHALAYA", "NAGALAND"));
        HERB_GROWING_REGIONS.put("CARDAMOM", Arrays.asList("KERALA", "KARNATAKA", "TAMIL_NADU"));
        HERB_GROWING_REGIONS.put("BLACK_PEPPER", Arrays.asList("KERALA", "KARNATAKA", "TAMIL_NADU"));
        HERB_GROWING_REGIONS.put("FENUGREEK", Arrays.asList("RAJASTHAN", "GUJARAT", "MADHYA_PRADESH", "UTTAR_PRADESH", "HARYANA"));
    }
    
    /**
     * Validate if coordinates are within global bounds
     */
    public boolean isValidGlobalCoordinates(BigDecimal latitude, BigDecimal longitude) {
        if (latitude == null || longitude == null) {
            return false;
        }
        
        try {
            double lat = latitude.doubleValue();
            double lng = longitude.doubleValue();
            
            return lat >= -90.0 && lat <= 90.0 && lng >= -180.0 && lng <= 180.0;
        } catch (Exception e) {
            logger.error("Error validating global coordinates", e);
            return false;
        }
    }
    
    /**
     * Validate if coordinates are within India's bounds
     */
    public boolean isValidIndianCoordinates(BigDecimal latitude, BigDecimal longitude) {
        if (!isValidGlobalCoordinates(latitude, longitude)) {
            return false;
        }
        
        try {
            double lat = latitude.doubleValue();
            double lng = longitude.doubleValue();
            
            // India's approximate bounds
            return lat >= 8.0 && lat <= 37.5 && lng >= 68.0 && lng <= 98.0;
        } catch (Exception e) {
            logger.error("Error validating Indian coordinates", e);
            return false;
        }
    }
    
    /**
     * Validate if coordinates are within a specific Indian state
     */
    public boolean isValidStateCoordinates(BigDecimal latitude, BigDecimal longitude, String state) {
        if (!isValidIndianCoordinates(latitude, longitude) || state == null) {
            return false;
        }
        
        String normalizedState = state.toUpperCase().replace(" ", "_");
        CoordinateBounds bounds = INDIAN_STATES_BOUNDS.get(normalizedState);
        
        if (bounds == null) {
            logger.warn("Unknown state: {}", state);
            return false;
        }
        
        try {
            double lat = latitude.doubleValue();
            double lng = longitude.doubleValue();
            
            return lat >= bounds.minLat && lat <= bounds.maxLat && 
                   lng >= bounds.minLng && lng <= bounds.maxLng;
        } catch (Exception e) {
            logger.error("Error validating state coordinates for state: {}", state, e);
            return false;
        }
    }
    
    /**
     * Get the state name from coordinates (approximate)
     */
    public String getStateFromCoordinates(BigDecimal latitude, BigDecimal longitude) {
        if (!isValidIndianCoordinates(latitude, longitude)) {
            return null;
        }
        
        double lat = latitude.doubleValue();
        double lng = longitude.doubleValue();
        
        for (Map.Entry<String, CoordinateBounds> entry : INDIAN_STATES_BOUNDS.entrySet()) {
            CoordinateBounds bounds = entry.getValue();
            if (lat >= bounds.minLat && lat <= bounds.maxLat && 
                lng >= bounds.minLng && lng <= bounds.maxLng) {
                return entry.getKey().replace("_", " ");
            }
        }
        
        return "UNKNOWN";
    }
    
    /**
     * Validate if herb can be grown in the specified location
     */
    public boolean isValidHerbLocation(String herbName, BigDecimal latitude, BigDecimal longitude) {
        if (herbName == null || !isValidIndianCoordinates(latitude, longitude)) {
            return false;
        }
        
        String normalizedHerb = herbName.toUpperCase().replace(" ", "_");
        String state = getStateFromCoordinates(latitude, longitude);
        
        if (state == null || "UNKNOWN".equals(state)) {
            return false;
        }
        
        List<String> growingRegions = HERB_GROWING_REGIONS.get(normalizedHerb);
        if (growingRegions == null) {
            // If herb is not in our database, allow it (assume it can grow anywhere in India)
            logger.info("Unknown herb: {}, allowing collection from any Indian location", herbName);
            return true;
        }
        
        String normalizedState = state.toUpperCase().replace(" ", "_");
        return growingRegions.contains(normalizedState);
    }
    
    /**
     * Get recommended growing regions for a herb
     */
    public List<String> getGrowingRegions(String herbName) {
        if (herbName == null) {
            return Arrays.asList();
        }
        
        String normalizedHerb = herbName.toUpperCase().replace(" ", "_");
        List<String> regions = HERB_GROWING_REGIONS.get(normalizedHerb);
        
        if (regions == null) {
            return Arrays.asList();
        }
        
        return regions.stream()
                     .map(region -> region.replace("_", " "))
                     .toList();
    }
    
    /**
     * Calculate distance between two points using Haversine formula
     */
    public double calculateDistance(BigDecimal lat1, BigDecimal lng1, BigDecimal lat2, BigDecimal lng2) {
        if (!isValidGlobalCoordinates(lat1, lng1) || !isValidGlobalCoordinates(lat2, lng2)) {
            return -1;
        }
        
        try {
            double earthRadius = 6371; // Earth radius in kilometers
            
            double dLat = Math.toRadians(lat2.doubleValue() - lat1.doubleValue());
            double dLng = Math.toRadians(lng2.doubleValue() - lng1.doubleValue());
            
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                      Math.cos(Math.toRadians(lat1.doubleValue())) * 
                      Math.cos(Math.toRadians(lat2.doubleValue())) *
                      Math.sin(dLng / 2) * Math.sin(dLng / 2);
            
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            
            return earthRadius * c;
        } catch (Exception e) {
            logger.error("Error calculating distance", e);
            return -1;
        }
    }
    
    /**
     * Check if location is in a biodiversity hotspot
     */
    public boolean isBiodiversityHotspot(BigDecimal latitude, BigDecimal longitude) {
        if (!isValidIndianCoordinates(latitude, longitude)) {
            return false;
        }
        
        String state = getStateFromCoordinates(latitude, longitude);
        
        // Indian biodiversity hotspots
        List<String> hotspots = Arrays.asList(
            "KERALA", "KARNATAKA", "TAMIL_NADU", // Western Ghats
            "ARUNACHAL_PRADESH", "ASSAM", "MEGHALAYA", "NAGALAND", "MANIPUR", "MIZORAM", "TRIPURA" // Eastern Himalayas
        );
        
        return hotspots.contains(state.replace(" ", "_"));
    }
    
    /**
     * Validate collection coordinates with detailed error messages
     */
    public ValidationResult validateCollectionLocation(BigDecimal latitude, BigDecimal longitude, 
                                                      String herbName, String expectedState) {
        ValidationResult result = new ValidationResult();
        
        if (!isValidGlobalCoordinates(latitude, longitude)) {
            result.setValid(false);
            result.addError("Invalid coordinate format");
            return result;
        }
        
        if (!isValidIndianCoordinates(latitude, longitude)) {
            result.setValid(false);
            result.addError("Coordinates are outside India");
            return result;
        }
        
        String detectedState = getStateFromCoordinates(latitude, longitude);
        result.setDetectedState(detectedState);
        
        if (expectedState != null && !expectedState.equalsIgnoreCase(detectedState)) {
            result.addWarning("Expected state: " + expectedState + ", but coordinates indicate: " + detectedState);
        }
        
        if (herbName != null && !isValidHerbLocation(herbName, latitude, longitude)) {
            result.addWarning("This herb is not commonly grown in " + detectedState + 
                            ". Recommended regions: " + String.join(", ", getGrowingRegions(herbName)));
        }
        
        if (isBiodiversityHotspot(latitude, longitude)) {
            result.addInfo("Location is in a biodiversity hotspot - ensure sustainable collection practices");
        }
        
        result.setValid(true);
        return result;
    }
    
    // Helper classes
    private static class CoordinateBounds {
        final double minLat, maxLat, minLng, maxLng;
        
        CoordinateBounds(double minLat, double maxLat, double minLng, double maxLng) {
            this.minLat = minLat;
            this.maxLat = maxLat;
            this.minLng = minLng;
            this.maxLng = maxLng;
        }
    }
    
    public static class ValidationResult {
        private boolean valid = true;
        private String detectedState;
        private List<String> errors = new java.util.ArrayList<>();
        private List<String> warnings = new java.util.ArrayList<>();
        private List<String> info = new java.util.ArrayList<>();
        
        // Getters and setters
        public boolean isValid() { return valid; }
        public void setValid(boolean valid) { this.valid = valid; }
        
        public String getDetectedState() { return detectedState; }
        public void setDetectedState(String detectedState) { this.detectedState = detectedState; }
        
        public List<String> getErrors() { return errors; }
        public List<String> getWarnings() { return warnings; }
        public List<String> getInfo() { return info; }
        
        public void addError(String error) { errors.add(error); }
        public void addWarning(String warning) { warnings.add(warning); }
        public void addInfo(String info) { this.info.add(info); }
    }
}
