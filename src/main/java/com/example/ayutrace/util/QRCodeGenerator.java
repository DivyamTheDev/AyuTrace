package com.example.ayutrace.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Component
public class QRCodeGenerator {
    
    private static final Logger logger = LoggerFactory.getLogger(QRCodeGenerator.class);
    
    @Value("${server.port:8080}")
    private String serverPort;
    
    @Value("${server.servlet.context-path:/api}")
    private String contextPath;
    
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 300;
    private static final String DEFAULT_FORMAT = "PNG";
    
    /**
     * Generate QR code for product batch provenance
     * 
     * @param batchId The batch ID to encode
     * @param width Width of the QR code image
     * @param height Height of the QR code image
     * @return Base64 encoded QR code image
     */
    public String generateQRCodeForBatch(String batchId, int width, int height) {
        try {
            String provenanceUrl = buildProvenanceUrl(batchId);
            return generateQRCode(provenanceUrl, width, height);
        } catch (Exception e) {
            logger.error("Failed to generate QR code for batch: {}", batchId, e);
            throw new RuntimeException("Failed to generate QR code", e);
        }
    }
    
    /**
     * Generate QR code with default dimensions
     * 
     * @param batchId The batch ID to encode
     * @return Base64 encoded QR code image
     */
    public String generateQRCodeForBatch(String batchId) {
        return generateQRCodeForBatch(batchId, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    /**
     * Generate QR code from any text content
     * 
     * @param content Content to encode in QR code
     * @param width Width of the QR code image
     * @param height Height of the QR code image
     * @return Base64 encoded QR code image
     */
    public String generateQRCode(String content, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            
            // Configure QR code generation hints
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 1);
            
            // Generate bit matrix
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            
            // Convert to image bytes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, DEFAULT_FORMAT, outputStream);
            
            // Convert to Base64
            byte[] imageBytes = outputStream.toByteArray();
            return java.util.Base64.getEncoder().encodeToString(imageBytes);
            
        } catch (WriterException | IOException e) {
            logger.error("Failed to generate QR code for content: {}", content, e);
            throw new RuntimeException("Failed to generate QR code", e);
        }
    }
    
    /**
     * Generate QR code and save to file system
     * 
     * @param content Content to encode
     * @param width Width of the QR code
     * @param height Height of the QR code
     * @param filePath Path where to save the QR code image
     * @return Path to the saved QR code file
     */
    public String generateQRCodeToFile(String content, int width, int height, String filePath) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 1);
            
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            
            Path path = Paths.get(filePath);
            // Ensure directory exists
            Files.createDirectories(path.getParent());
            
            MatrixToImageWriter.writeToPath(bitMatrix, DEFAULT_FORMAT, path);
            
            logger.info("QR code saved to: {}", filePath);
            return filePath;
            
        } catch (WriterException | IOException e) {
            logger.error("Failed to save QR code to file: {}", filePath, e);
            throw new RuntimeException("Failed to save QR code to file", e);
        }
    }
    
    /**
     * Generate unique QR code identifier for a batch
     * 
     * @param batchId The batch ID
     * @return Unique QR code identifier
     */
    public String generateQRCodeId(String batchId) {
        // Generate a unique QR code ID using batch ID and current timestamp
        long timestamp = System.currentTimeMillis();
        return String.format("QR_%s_%d", batchId.replaceAll("[^a-zA-Z0-9]", ""), timestamp);
    }
    
    /**
     * Build provenance URL for consumer scanning
     * 
     * @param batchId The batch ID
     * @return Full URL for provenance lookup
     */
    private String buildProvenanceUrl(String batchId) {
        // Build the complete URL that consumers will access when scanning QR code
        return String.format("http://localhost:%s%s/provenance/consumer/%s", 
                            serverPort, contextPath, batchId);
    }
    
    /**
     * Generate QR code data with batch information
     * 
     * @param batchId Batch ID
     * @param productName Product name
     * @param manufacturerName Manufacturer name
     * @return JSON string with batch information
     */
    public String generateQRCodeData(String batchId, String productName, String manufacturerName) {
        // Create a JSON-like string with essential batch information
        return String.format("{\n" +
                "  \"type\": \"AyurTrace_Batch\",\n" +
                "  \"batchId\": \"%s\",\n" +
                "  \"productName\": \"%s\",\n" +
                "  \"manufacturer\": \"%s\",\n" +
                "  \"verificationUrl\": \"%s\",\n" +
                "  \"scanTime\": \"%s\"\n" +
                "}", 
                batchId, 
                productName, 
                manufacturerName, 
                buildProvenanceUrl(batchId),
                java.time.Instant.now().toString());
    }
    
    /**
     * Validate QR code content
     * 
     * @param content QR code content to validate
     * @return true if content is valid for AyurTrace
     */
    public boolean isValidQRContent(String content) {
        try {
            // Check if content contains our provenance URL pattern
            return content != null && 
                   !content.trim().isEmpty() && 
                   (content.contains("/provenance/") || content.contains("AyurTrace_Batch"));
        } catch (Exception e) {
            logger.error("Error validating QR content: {}", content, e);
            return false;
        }
    }
    
    /**
     * Extract batch ID from QR code content
     * 
     * @param qrContent QR code content
     * @return Batch ID if found, null otherwise
     */
    public String extractBatchIdFromQRContent(String qrContent) {
        try {
            if (qrContent.contains("/provenance/consumer/")) {
                // Extract from URL
                String[] parts = qrContent.split("/provenance/consumer/");
                if (parts.length > 1) {
                    return parts[1].split("[?&]")[0]; // Remove query parameters if any
                }
            } else if (qrContent.contains("\"batchId\":")) {
                // Extract from JSON format
                String[] parts = qrContent.split("\"batchId\":");
                if (parts.length > 1) {
                    String batchPart = parts[1].trim();
                    if (batchPart.startsWith("\"")) {
                        return batchPart.substring(1).split("\"")[0];
                    }
                }
            }
            return null;
        } catch (Exception e) {
            logger.error("Error extracting batch ID from QR content: {}", qrContent, e);
            return null;
        }
    }
    
    /**
     * Generate QR code with custom error correction level
     * 
     * @param content Content to encode
     * @param width QR code width
     * @param height QR code height
     * @param errorCorrectionLevel Error correction level
     * @return Base64 encoded QR code
     */
    public String generateQRCodeWithErrorCorrection(String content, int width, int height, 
                                                   ErrorCorrectionLevel errorCorrectionLevel) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 1);
            
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, DEFAULT_FORMAT, outputStream);
            
            byte[] imageBytes = outputStream.toByteArray();
            return java.util.Base64.getEncoder().encodeToString(imageBytes);
            
        } catch (WriterException | IOException e) {
            logger.error("Failed to generate QR code with custom error correction", e);
            throw new RuntimeException("Failed to generate QR code", e);
        }
    }
}
