package com.example.ayutrace.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@Component
public class AyurTraceUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(AyurTraceUtils.class);
    
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String NUMERIC = "0123456789";
    
    // ID Patterns
    private static final String COLLECTION_PREFIX = "COL";
    private static final String PROCESSING_PREFIX = "PRO";
    private static final String TEST_PREFIX = "TST";
    private static final String BATCH_PREFIX = "BAT";
    private static final String QR_PREFIX = "QR";
    
    // Validation patterns
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$"
    );
    
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^[+]?[1-9]\\d{1,14}$"
    );
    
    /**
     * Generate unique collection ID
     * 
     * @return Unique collection ID (e.g., COL20241201001)
     */
    public String generateCollectionId() {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String random = generateRandomNumeric(3);
        return String.format("%s%s%s", COLLECTION_PREFIX, dateTime, random);
    }
    
    /**
     * Generate unique processing ID
     * 
     * @return Unique processing ID (e.g., PRO20241201001)
     */
    public String generateProcessingId() {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String random = generateRandomNumeric(3);
        return String.format("%s%s%s", PROCESSING_PREFIX, dateTime, random);
    }
    
    /**
     * Generate unique test ID
     * 
     * @return Unique test ID (e.g., TST20241201001)
     */
    public String generateTestId() {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String random = generateRandomNumeric(3);
        return String.format("%s%s%s", TEST_PREFIX, dateTime, random);
    }
    
    /**
     * Generate unique batch ID
     * 
     * @return Unique batch ID (e.g., BAT20241201001)
     */
    public String generateBatchId() {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String random = generateRandomAlphanumeric(4);
        return String.format("%s%s%s", BATCH_PREFIX, dateTime, random);
    }
    
    /**
     * Generate unique QR code
     * 
     * @return Unique QR code (e.g., QR20241201ABC123)
     */
    public String generateQRCode() {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String random = generateRandomAlphanumeric(6);
        return String.format("%s%s%s", QR_PREFIX, dateTime, random);
    }
    
    /**
     * Generate random alphanumeric string
     * 
     * @param length Length of the string
     * @return Random alphanumeric string
     */
    public String generateRandomAlphanumeric(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(ALPHANUMERIC.length());
            sb.append(ALPHANUMERIC.charAt(index));
        }
        return sb.toString();
    }
    
    /**
     * Generate random numeric string
     * 
     * @param length Length of the string
     * @return Random numeric string
     */
    public String generateRandomNumeric(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(NUMERIC.length());
            sb.append(NUMERIC.charAt(index));
        }
        return sb.toString();
    }
    
    /**
     * Validate geographic coordinates
     * 
     * @param latitude Latitude value
     * @param longitude Longitude value
     * @return true if coordinates are valid
     */
    public boolean isValidCoordinates(BigDecimal latitude, BigDecimal longitude) {
        try {
            if (latitude == null || longitude == null) {
                return false;
            }
            
            // Check latitude range: -90 to 90
            if (latitude.compareTo(new BigDecimal("-90")) < 0 || 
                latitude.compareTo(new BigDecimal("90")) > 0) {
                return false;
            }
            
            // Check longitude range: -180 to 180
            if (longitude.compareTo(new BigDecimal("-180")) < 0 || 
                longitude.compareTo(new BigDecimal("180")) > 0) {
                return false;
            }
            
            return true;
        } catch (Exception e) {
            logger.error("Error validating coordinates: lat={}, lng={}", latitude, longitude, e);
            return false;
        }
    }
    
    /**
     * Validate coordinates are within India's bounds (approximate)
     * 
     * @param latitude Latitude value
     * @param longitude Longitude value
     * @return true if coordinates are within India
     */
    public boolean isValidIndianCoordinates(BigDecimal latitude, BigDecimal longitude) {
        if (!isValidCoordinates(latitude, longitude)) {
            return false;
        }
        
        try {
            // Approximate bounds for India
            // Latitude: 8.4°N to 37.1°N (Kashmir to Kanyakumari)
            // Longitude: 68.2°E to 97.4°E (Gujarat to Arunachal Pradesh)
            
            BigDecimal minLat = new BigDecimal("8.4");
            BigDecimal maxLat = new BigDecimal("37.1");
            BigDecimal minLng = new BigDecimal("68.2");
            BigDecimal maxLng = new BigDecimal("97.4");
            
            return latitude.compareTo(minLat) >= 0 && latitude.compareTo(maxLat) <= 0 &&
                   longitude.compareTo(minLng) >= 0 && longitude.compareTo(maxLng) <= 0;
                   
        } catch (Exception e) {
            logger.error("Error validating Indian coordinates", e);
            return false;
        }
    }
    
    /**
     * Validate email address
     * 
     * @param email Email address to validate
     * @return true if email is valid
     */
    public boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email.trim()).matches();
    }
    
    /**
     * Validate phone number
     * 
     * @param phoneNumber Phone number to validate
     * @return true if phone number is valid
     */
    public boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        
        String cleanPhone = phoneNumber.trim().replaceAll("\\s+", "");
        return PHONE_PATTERN.matcher(cleanPhone).matches();
    }
    
    /**
     * Validate Indian PIN code
     * 
     * @param pinCode PIN code to validate
     * @return true if PIN code is valid
     */
    public boolean isValidIndianPinCode(String pinCode) {
        if (pinCode == null) {
            return false;
        }
        
        String cleanPin = pinCode.trim();
        return cleanPin.matches("^[1-9][0-9]{5}$");
    }
    
    /**
     * Sanitize string for database storage
     * 
     * @param input Input string
     * @return Sanitized string
     */
    public String sanitizeString(String input) {
        if (input == null) {
            return null;
        }
        
        return input.trim()
                   .replaceAll("\\s+", " ") // Replace multiple spaces with single space
                   .replaceAll("[\\r\\n]+", " "); // Replace newlines with space
    }
    
    /**
     * Calculate distance between two geographical points using Haversine formula
     * 
     * @param lat1 Latitude of first point
     * @param lng1 Longitude of first point
     * @param lat2 Latitude of second point
     * @param lng2 Longitude of second point
     * @return Distance in kilometers
     */
    public double calculateDistance(BigDecimal lat1, BigDecimal lng1, BigDecimal lat2, BigDecimal lng2) {
        if (!isValidCoordinates(lat1, lng1) || !isValidCoordinates(lat2, lng2)) {
            return -1; // Invalid coordinates
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
            logger.error("Error calculating distance between coordinates", e);
            return -1;
        }
    }
    
    /**
     * Format quantity for display
     * 
     * @param quantity Quantity in kg
     * @return Formatted quantity string
     */
    public String formatQuantity(BigDecimal quantity) {
        if (quantity == null) {
            return "0 kg";
        }
        
        if (quantity.compareTo(BigDecimal.ONE) < 0) {
            // Less than 1 kg, show in grams
            BigDecimal grams = quantity.multiply(new BigDecimal("1000"));
            return String.format("%.0f g", grams.doubleValue());
        } else {
            // 1 kg or more, show in kg
            return String.format("%.2f kg", quantity.doubleValue());
        }
    }
    
    /**
     * Generate batch number based on date and sequence
     * 
     * @param sequence Sequence number for the day
     * @return Batch number (e.g., 20241201-001)
     */
    public String generateBatchNumber(int sequence) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return String.format("%s-%03d", date, sequence);
    }
    
    /**
     * Mask sensitive information (like phone numbers, emails)
     * 
     * @param input Sensitive information to mask
     * @param visibleChars Number of characters to keep visible
     * @return Masked string
     */
    public String maskSensitiveInfo(String input, int visibleChars) {
        if (input == null || input.length() <= visibleChars) {
            return input;
        }
        
        StringBuilder masked = new StringBuilder();
        masked.append(input, 0, visibleChars);
        masked.append("*".repeat(Math.max(0, input.length() - visibleChars)));
        
        return masked.toString();
    }
    
    /**
     * Check if string is null or empty
     * 
     * @param str String to check
     * @return true if string is null or empty
     */
    public boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * Generate certificate number
     * 
     * @param type Certificate type (e.g., "LAB", "GMP", "ORGANIC")
     * @return Certificate number
     */
    public String generateCertificateNumber(String type) {
        String year = String.valueOf(LocalDateTime.now().getYear());
        String random = generateRandomAlphanumeric(6);
        return String.format("%s/%s/%s", type.toUpperCase(), year, random);
    }
}
