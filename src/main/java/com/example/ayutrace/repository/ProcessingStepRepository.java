package com.example.ayutrace.repository;

import com.example.ayutrace.model.CollectionEvent;
import com.example.ayutrace.model.ProcessingStep;
import com.example.ayutrace.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessingStepRepository extends JpaRepository<ProcessingStep, Long> {
    
    // Basic queries
    Optional<ProcessingStep> findByProcessingId(String processingId);
    List<ProcessingStep> findByProcessor(User processor);
    List<ProcessingStep> findByCollectionEvent(CollectionEvent collectionEvent);
    Page<ProcessingStep> findByProcessor(User processor, Pageable pageable);
    
    // Processing type queries
    List<ProcessingStep> findByStepType(ProcessingStep.ProcessingType stepType);
    Page<ProcessingStep> findByStepType(ProcessingStep.ProcessingType stepType, Pageable pageable);
    
    @Query("SELECT p FROM ProcessingStep p WHERE p.stepType IN :stepTypes ORDER BY p.processingDate DESC")
    List<ProcessingStep> findByStepTypeIn(@Param("stepTypes") List<ProcessingStep.ProcessingType> stepTypes);
    
    // Status-based queries
    List<ProcessingStep> findByStatus(ProcessingStep.ProcessingStatus status);
    Page<ProcessingStep> findByStatus(ProcessingStep.ProcessingStatus status, Pageable pageable);
    
    @Query("SELECT p FROM ProcessingStep p WHERE p.status IN :statuses ORDER BY p.processingDate DESC")
    List<ProcessingStep> findByStatusIn(@Param("statuses") List<ProcessingStep.ProcessingStatus> statuses);
    
    // Date-based queries
    List<ProcessingStep> findByProcessingDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<ProcessingStep> findByProcessingDateAfter(LocalDateTime date);
    List<ProcessingStep> findByProcessingDateBefore(LocalDateTime date);
    
    @Query("SELECT p FROM ProcessingStep p WHERE YEAR(p.processingDate) = :year AND MONTH(p.processingDate) = :month")
    List<ProcessingStep> findByProcessingMonth(@Param("year") int year, @Param("month") int month);
    
    // Batch queries
    List<ProcessingStep> findByBatchNumber(String batchNumber);
    
    @Query("SELECT DISTINCT p.batchNumber FROM ProcessingStep p WHERE p.batchNumber IS NOT NULL ORDER BY p.batchNumber")
    List<String> findDistinctBatchNumbers();
    
    // Quantity and yield queries
    @Query("SELECT p FROM ProcessingStep p WHERE p.yieldPercentage >= :minYield")
    List<ProcessingStep> findByMinimumYield(@Param("minYield") BigDecimal minYield);
    
    @Query("SELECT p FROM ProcessingStep p WHERE p.yieldPercentage BETWEEN :minYield AND :maxYield")
    List<ProcessingStep> findByYieldRange(@Param("minYield") BigDecimal minYield, @Param("maxYield") BigDecimal maxYield);
    
    @Query("SELECT p FROM ProcessingStep p WHERE p.outputQuantityKg >= :minOutput")
    List<ProcessingStep> findByMinimumOutput(@Param("minOutput") BigDecimal minOutput);
    
    // Quality grade queries
    List<ProcessingStep> findByQualityGrade(String qualityGrade);
    
    @Query("SELECT p FROM ProcessingStep p WHERE p.qualityGrade IN :grades")
    List<ProcessingStep> findByQualityGradeIn(@Param("grades") List<String> grades);
    
    // Equipment and process parameter queries
    List<ProcessingStep> findByEquipmentUsed(String equipmentUsed);
    List<ProcessingStep> findByEquipmentUsedContainingIgnoreCase(String equipment);
    
    @Query("SELECT p FROM ProcessingStep p WHERE p.temperatureCelsius BETWEEN :minTemp AND :maxTemp")
    List<ProcessingStep> findByTemperatureRange(@Param("minTemp") Integer minTemp, @Param("maxTemp") Integer maxTemp);
    
    @Query("SELECT p FROM ProcessingStep p WHERE p.humidityPercentage BETWEEN :minHumidity AND :maxHumidity")
    List<ProcessingStep> findByHumidityRange(@Param("minHumidity") Integer minHumidity, @Param("maxHumidity") Integer maxHumidity);
    
    @Query("SELECT p FROM ProcessingStep p WHERE p.processingDurationHours >= :minDuration")
    List<ProcessingStep> findByMinimumDuration(@Param("minDuration") Integer minDuration);
    
    // Compliance queries
    List<ProcessingStep> findByComplianceStandards(String complianceStandards);
    
    @Query("SELECT p FROM ProcessingStep p WHERE p.complianceStandards LIKE %:standard%")
    List<ProcessingStep> findByComplianceStandardContaining(@Param("standard") String standard);
    
    // Search queries
    @Query("SELECT p FROM ProcessingStep p WHERE " +
           "(LOWER(p.processingId) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.stepName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.batchNumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.equipmentUsed) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "ORDER BY p.processingDate DESC")
    Page<ProcessingStep> searchProcessingSteps(@Param("search") String search, Pageable pageable);
    
    // Statistics queries
    @Query("SELECT COUNT(p) FROM ProcessingStep p WHERE p.processor = :processor")
    Long countByProcessor(@Param("processor") User processor);
    
    @Query("SELECT p.stepType, COUNT(p) FROM ProcessingStep p WHERE p.processingDate >= :startDate GROUP BY p.stepType")
    List<Object[]> getStepTypeStatistics(@Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT p.status, COUNT(p) FROM ProcessingStep p WHERE p.processingDate >= :startDate GROUP BY p.status")
    List<Object[]> getStatusStatistics(@Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT p.qualityGrade, COUNT(p) FROM ProcessingStep p WHERE p.qualityGrade IS NOT NULL AND p.processingDate >= :startDate GROUP BY p.qualityGrade")
    List<Object[]> getQualityGradeStatistics(@Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT AVG(p.yieldPercentage) FROM ProcessingStep p WHERE p.yieldPercentage IS NOT NULL AND p.stepType = :stepType")
    BigDecimal getAverageYieldByStepType(@Param("stepType") ProcessingStep.ProcessingType stepType);
    
    @Query("SELECT SUM(p.inputQuantityKg), SUM(p.outputQuantityKg) FROM ProcessingStep p WHERE p.processor = :processor")
    List<Object[]> getTotalQuantityByProcessor(@Param("processor") User processor);
    
    // Recent activity queries
    @Query("SELECT p FROM ProcessingStep p ORDER BY p.processingDate DESC")
    Page<ProcessingStep> findRecentProcessingSteps(Pageable pageable);
    
    @Query("SELECT p FROM ProcessingStep p WHERE p.createdAt >= :since ORDER BY p.createdAt DESC")
    List<ProcessingStep> findRecentlyCreated(@Param("since") LocalDateTime since);
    
    // Collection event related queries
    @Query("SELECT p FROM ProcessingStep p WHERE p.collectionEvent.herbName = :herbName ORDER BY p.processingDate DESC")
    List<ProcessingStep> findByHerbName(@Param("herbName") String herbName);
    
    @Query("SELECT p FROM ProcessingStep p WHERE p.collectionEvent.collector = :collector ORDER BY p.processingDate DESC")
    List<ProcessingStep> findByOriginalCollector(@Param("collector") User collector);
    
    @Query("SELECT p FROM ProcessingStep p WHERE p.collectionEvent.collectionLocation LIKE %:location% ORDER BY p.processingDate DESC")
    List<ProcessingStep> findByOriginalLocation(@Param("location") String location);
    
    // Monthly statistics for dashboard
    @Query("SELECT YEAR(p.processingDate), MONTH(p.processingDate), COUNT(p), " +
           "SUM(p.inputQuantityKg), SUM(p.outputQuantityKg), AVG(p.yieldPercentage) " +
           "FROM ProcessingStep p WHERE p.processingDate >= :startDate " +
           "GROUP BY YEAR(p.processingDate), MONTH(p.processingDate) " +
           "ORDER BY YEAR(p.processingDate), MONTH(p.processingDate)")
    List<Object[]> getMonthlyStatistics(@Param("startDate") LocalDateTime startDate);
    
    // Processing chain queries
    @Query("SELECT p FROM ProcessingStep p WHERE p.collectionEvent = :collectionEvent ORDER BY p.processingDate ASC")
    List<ProcessingStep> findProcessingChain(@Param("collectionEvent") CollectionEvent collectionEvent);
    
    // In-progress processing steps
    @Query("SELECT p FROM ProcessingStep p WHERE p.status = 'IN_PROGRESS' ORDER BY p.startTime ASC")
    List<ProcessingStep> findInProgressSteps();
    
    // Completed processing steps ready for next stage
    @Query("SELECT p FROM ProcessingStep p WHERE p.status = 'COMPLETED' AND p.stepType = :stepType ORDER BY p.processingDate DESC")
    List<ProcessingStep> findCompletedStepsByType(@Param("stepType") ProcessingStep.ProcessingType stepType);
    
    // Steps requiring attention
    @Query("SELECT p FROM ProcessingStep p WHERE p.status = 'ON_HOLD' OR p.status = 'REWORK_REQUIRED' ORDER BY p.processingDate DESC")
    List<ProcessingStep> findStepsRequiringAttention();
    
    // Processing efficiency queries
    @Query("SELECT p.processor.fullName, AVG(p.yieldPercentage) FROM ProcessingStep p " +
           "WHERE p.yieldPercentage IS NOT NULL AND p.processingDate >= :startDate " +
           "GROUP BY p.processor.fullName ORDER BY AVG(p.yieldPercentage) DESC")
    List<Object[]> getProcessorEfficiency(@Param("startDate") LocalDateTime startDate);
    
    // Equipment utilization
    @Query("SELECT p.equipmentUsed, COUNT(p) FROM ProcessingStep p " +
           "WHERE p.equipmentUsed IS NOT NULL AND p.processingDate >= :startDate " +
           "GROUP BY p.equipmentUsed ORDER BY COUNT(p) DESC")
    List<Object[]> getEquipmentUtilization(@Param("startDate") LocalDateTime startDate);
}
