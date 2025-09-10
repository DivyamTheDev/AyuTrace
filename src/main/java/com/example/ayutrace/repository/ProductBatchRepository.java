package com.example.ayutrace.repository;

import com.example.ayutrace.model.ProductBatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductBatchRepository extends JpaRepository<ProductBatch, Long> {
    
    // Basic queries
    Optional<ProductBatch> findByBatchId(String batchId);
    Optional<ProductBatch> findByQrCode(String qrCode);
    Optional<ProductBatch> findByBarcode(String barcode);
    
    // Product queries
    List<ProductBatch> findByProductName(String productName);
    List<ProductBatch> findByProductNameContainingIgnoreCase(String productName);
    List<ProductBatch> findByBrandName(String brandName);
    
    @Query("SELECT DISTINCT b.productName FROM ProductBatch b ORDER BY b.productName")
    List<String> findDistinctProductNames();
    
    @Query("SELECT DISTINCT b.brandName FROM ProductBatch b WHERE b.brandName IS NOT NULL ORDER BY b.brandName")
    List<String> findDistinctBrandNames();
    
    // Manufacturer queries
    List<ProductBatch> findByManufacturerName(String manufacturerName);
    List<ProductBatch> findByManufacturerLicense(String manufacturerLicense);
    
    @Query("SELECT DISTINCT b.manufacturerName FROM ProductBatch b ORDER BY b.manufacturerName")
    List<String> findDistinctManufacturers();
    
    // Product form queries
    List<ProductBatch> findByProductForm(ProductBatch.ProductForm productForm);
    Page<ProductBatch> findByProductForm(ProductBatch.ProductForm productForm, Pageable pageable);
    
    // Status queries
    List<ProductBatch> findByStatus(ProductBatch.BatchStatus status);
    Page<ProductBatch> findByStatus(ProductBatch.BatchStatus status, Pageable pageable);
    
    @Query("SELECT b FROM ProductBatch b WHERE b.status IN :statuses ORDER BY b.manufacturingDate DESC")
    List<ProductBatch> findByStatusIn(@Param("statuses") List<ProductBatch.BatchStatus> statuses);
    
    // Date-based queries
    List<ProductBatch> findByManufacturingDateBetween(LocalDate startDate, LocalDate endDate);
    List<ProductBatch> findByManufacturingDateAfter(LocalDate date);
    List<ProductBatch> findByManufacturingDateBefore(LocalDate date);
    
    List<ProductBatch> findByExpiryDateBetween(LocalDate startDate, LocalDate endDate);
    List<ProductBatch> findByExpiryDateAfter(LocalDate date);
    List<ProductBatch> findByExpiryDateBefore(LocalDate date);
    
    @Query("SELECT b FROM ProductBatch b WHERE b.expiryDate <= :date AND b.status != 'EXPIRED'")
    List<ProductBatch> findExpiringBatches(@Param("date") LocalDate date);
    
    @Query("SELECT b FROM ProductBatch b WHERE b.expiryDate < CURRENT_DATE AND b.status != 'EXPIRED'")
    List<ProductBatch> findExpiredBatches();
    
    // Quality and approval queries
    List<ProductBatch> findByBatchTestingCompletedTrue();
    List<ProductBatch> findByBatchTestingCompletedFalse();
    List<ProductBatch> findByReleaseApprovedTrue();
    List<ProductBatch> findByReleaseApprovedFalse();
    
    @Query("SELECT b FROM ProductBatch b WHERE b.batchTestingCompleted = true AND b.releaseApproved = false")
    List<ProductBatch> findBatchesAwaitingApproval();
    
    List<ProductBatch> findByApprovedBy(String approvedBy);
    List<ProductBatch> findByFinalQualityGrade(String finalQualityGrade);
    
    // Blockchain and verification queries
    List<ProductBatch> findByBlockchainVerifiedTrue();
    List<ProductBatch> findByBlockchainVerifiedFalse();
    
    @Query("SELECT b FROM ProductBatch b WHERE b.blockchainHash IS NOT NULL AND b.blockchainVerified = false")
    List<ProductBatch> findBatchesPendingBlockchainVerification();
    
    @Query("SELECT b FROM ProductBatch b WHERE b.traceabilityScore >= :minScore")
    List<ProductBatch> findByMinimumTraceabilityScore(@Param("minScore") BigDecimal minScore);
    
    @Query("SELECT b FROM ProductBatch b WHERE b.sustainabilityScore >= :minScore")
    List<ProductBatch> findByMinimumSustainabilityScore(@Param("minScore") BigDecimal minScore);
    
    // Certification queries
    @Query("SELECT b FROM ProductBatch b WHERE b.ayushLicense IS NOT NULL")
    List<ProductBatch> findWithAyushLicense();
    
    @Query("SELECT b FROM ProductBatch b WHERE b.gmpCertificate IS NOT NULL")
    List<ProductBatch> findWithGMPCertificate();
    
    @Query("SELECT b FROM ProductBatch b WHERE b.organicCertification IS NOT NULL")
    List<ProductBatch> findWithOrganicCertification();
    
    // Quantity queries
    @Query("SELECT b FROM ProductBatch b WHERE b.unitsProduced >= :minUnits")
    List<ProductBatch> findByMinimumUnitsProduced(@Param("minUnits") Integer minUnits);
    
    @Query("SELECT b FROM ProductBatch b WHERE b.totalQuantityKg >= :minQuantity")
    List<ProductBatch> findByMinimumTotalQuantity(@Param("minQuantity") BigDecimal minQuantity);
    
    // Search queries
    @Query("SELECT b FROM ProductBatch b WHERE " +
           "(LOWER(b.batchId) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(b.productName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(b.brandName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(b.manufacturerName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(b.qrCode) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "ORDER BY b.manufacturingDate DESC")
    Page<ProductBatch> searchProductBatches(@Param("search") String search, Pageable pageable);
    
    // Statistics queries
    @Query("SELECT COUNT(b) FROM ProductBatch b WHERE b.manufacturerName = :manufacturer")
    Long countByManufacturer(@Param("manufacturer") String manufacturer);
    
    @Query("SELECT b.status, COUNT(b) FROM ProductBatch b WHERE b.manufacturingDate >= :startDate GROUP BY b.status")
    List<Object[]> getBatchStatusStatistics(@Param("startDate") LocalDate startDate);
    
    @Query("SELECT b.productForm, COUNT(b) FROM ProductBatch b WHERE b.manufacturingDate >= :startDate GROUP BY b.productForm")
    List<Object[]> getProductFormStatistics(@Param("startDate") LocalDate startDate);
    
    @Query("SELECT b.manufacturerName, COUNT(b) FROM ProductBatch b WHERE b.manufacturingDate >= :startDate GROUP BY b.manufacturerName ORDER BY COUNT(b) DESC")
    List<Object[]> getManufacturerStatistics(@Param("startDate") LocalDate startDate);
    
    @Query("SELECT SUM(b.unitsProduced) FROM ProductBatch b WHERE b.manufacturerName = :manufacturer")
    Long getTotalUnitsProducedByManufacturer(@Param("manufacturer") String manufacturer);
    
    // Recent activity queries
    @Query("SELECT b FROM ProductBatch b ORDER BY b.manufacturingDate DESC")
    Page<ProductBatch> findRecentBatches(Pageable pageable);
    
    @Query("SELECT b FROM ProductBatch b WHERE b.createdAt >= :since ORDER BY b.createdAt DESC")
    List<ProductBatch> findRecentlyCreated(@Param("since") LocalDateTime since);
    
    @Query("SELECT b FROM ProductBatch b WHERE b.updatedAt >= :since AND b.updatedAt > b.createdAt ORDER BY b.updatedAt DESC")
    List<ProductBatch> findRecentlyUpdated(@Param("since") LocalDateTime since);
    
    // Monthly statistics for dashboard
    @Query("SELECT YEAR(b.manufacturingDate), MONTH(b.manufacturingDate), COUNT(b), SUM(b.unitsProduced), SUM(b.totalQuantityKg) " +
           "FROM ProductBatch b WHERE b.manufacturingDate >= :startDate " +
           "GROUP BY YEAR(b.manufacturingDate), MONTH(b.manufacturingDate) " +
           "ORDER BY YEAR(b.manufacturingDate), MONTH(b.manufacturingDate)")
    List<Object[]> getMonthlyProductionStatistics(@Param("startDate") LocalDate startDate);
    
    // Quality control queries
    @Query("SELECT b FROM ProductBatch b WHERE b.finalQualityGrade IN :acceptableGrades AND b.releaseApproved = true")
    List<ProductBatch> findReleasedBatchesWithAcceptableGrade(@Param("acceptableGrades") List<String> grades);
    
    @Query("SELECT b FROM ProductBatch b WHERE b.batchTestingCompleted = false ORDER BY b.manufacturingDate ASC")
    List<ProductBatch> findBatchesAwaitingTesting();
    
    // Recall queries
    @Query("SELECT b FROM ProductBatch b WHERE b.status = 'RECALLED' ORDER BY b.updatedAt DESC")
    List<ProductBatch> findRecalledBatches();
    
    // Traceability queries
    @Query("SELECT b FROM ProductBatch b JOIN b.sourceCollections c WHERE c.herbName = :herbName")
    List<ProductBatch> findBySourceHerb(@Param("herbName") String herbName);
    
    @Query("SELECT b FROM ProductBatch b JOIN b.sourceCollections c WHERE c.collectionLocation LIKE %:location%")
    List<ProductBatch> findBySourceLocation(@Param("location") String location);
    
    @Query("SELECT b FROM ProductBatch b JOIN b.qualityTests q WHERE q.testResult = 'PASS'")
    List<ProductBatch> findBatchesWithPassedTests();
    
    @Query("SELECT b FROM ProductBatch b JOIN b.qualityTests q WHERE q.adulterationDetected = true")
    List<ProductBatch> findBatchesWithAdulterationDetected();
    
    // Distribution queries
    @Query("SELECT b FROM ProductBatch b WHERE b.status = 'DISTRIBUTED' AND b.manufacturingDate >= :startDate")
    List<ProductBatch> findDistributedBatches(@Param("startDate") LocalDate startDate);
    
    // Price queries
    @Query("SELECT b FROM ProductBatch b WHERE b.mrp BETWEEN :minPrice AND :maxPrice")
    List<ProductBatch> findByPriceRange(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
    
    @Query("SELECT AVG(b.mrp) FROM ProductBatch b WHERE b.productForm = :productForm")
    BigDecimal getAveragePriceByProductForm(@Param("productForm") ProductBatch.ProductForm productForm);
    
    // Consumer lookup by QR code
    @Query("SELECT b FROM ProductBatch b WHERE b.qrCode = :qrCode AND b.releaseApproved = true")
    Optional<ProductBatch> findByQrCodeForConsumer(@Param("qrCode") String qrCode);
}
