package com.example.ayutrace.repository;

import com.example.ayutrace.model.CollectionEvent;
import com.example.ayutrace.model.QualityTest;
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
public interface QualityTestRepository extends JpaRepository<QualityTest, Long> {
    
    // Basic queries
    Optional<QualityTest> findByTestId(String testId);
    List<QualityTest> findByTester(User tester);
    List<QualityTest> findByCollectionEvent(CollectionEvent collectionEvent);
    Page<QualityTest> findByTester(User tester, Pageable pageable);
    
    // Test type queries
    List<QualityTest> findByTestType(QualityTest.TestType testType);
    Page<QualityTest> findByTestType(QualityTest.TestType testType, Pageable pageable);
    
    @Query("SELECT q FROM QualityTest q WHERE q.testType IN :testTypes ORDER BY q.testDate DESC")
    List<QualityTest> findByTestTypeIn(@Param("testTypes") List<QualityTest.TestType> testTypes);
    
    // Lab-based queries
    List<QualityTest> findByLabName(String labName);
    List<QualityTest> findByLabNameContainingIgnoreCase(String labKeyword);
    
    @Query("SELECT DISTINCT q.labName FROM QualityTest q ORDER BY q.labName")
    List<String> findDistinctLabNames();
    
    @Query("SELECT q FROM QualityTest q WHERE q.labAccreditation = :accreditation")
    List<QualityTest> findByLabAccreditation(@Param("accreditation") String accreditation);
    
    // Date-based queries
    List<QualityTest> findByTestDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<QualityTest> findByTestDateAfter(LocalDateTime date);
    List<QualityTest> findByTestDateBefore(LocalDateTime date);
    
    @Query("SELECT q FROM QualityTest q WHERE YEAR(q.testDate) = :year AND MONTH(q.testDate) = :month")
    List<QualityTest> findByTestMonth(@Param("year") int year, @Param("month") int month);
    
    // Result-based queries
    List<QualityTest> findByTestResult(QualityTest.TestResult testResult);
    Page<QualityTest> findByTestResult(QualityTest.TestResult testResult, Pageable pageable);
    
    @Query("SELECT q FROM QualityTest q WHERE q.testResult IN :results ORDER BY q.testDate DESC")
    List<QualityTest> findByTestResultIn(@Param("results") List<QualityTest.TestResult> results);
    
    // Grade-based queries
    List<QualityTest> findByGradeAssigned(String grade);
    
    @Query("SELECT q FROM QualityTest q WHERE q.gradeAssigned IN :grades")
    List<QualityTest> findByGradeIn(@Param("grades") List<String> grades);
    
    // Physical test parameter queries
    @Query("SELECT q FROM QualityTest q WHERE q.moistureContent BETWEEN :minMoisture AND :maxMoisture")
    List<QualityTest> findByMoistureContentRange(@Param("minMoisture") BigDecimal min, @Param("maxMoisture") BigDecimal max);
    
    @Query("SELECT q FROM QualityTest q WHERE q.ashContent <= :maxAsh")
    List<QualityTest> findByAshContentLessThanEqual(@Param("maxAsh") BigDecimal maxAsh);
    
    // Chemical test queries
    @Query("SELECT q FROM QualityTest q WHERE q.aflatoxins > :threshold")
    List<QualityTest> findByAflatoxinsGreaterThan(@Param("threshold") BigDecimal threshold);
    
    @Query("SELECT q FROM QualityTest q WHERE q.phValue BETWEEN :minPh AND :maxPh")
    List<QualityTest> findByPhValueRange(@Param("minPh") BigDecimal minPh, @Param("maxPh") BigDecimal maxPh);
    
    // Microbiological test queries
    @Query("SELECT q FROM QualityTest q WHERE q.ecoliPresence = true OR q.salmonellaPresence = true")
    List<QualityTest> findWithPathogenPresence();
    
    @Query("SELECT q FROM QualityTest q WHERE q.totalBacterialCount > :threshold")
    List<QualityTest> findByHighBacterialCount(@Param("threshold") Integer threshold);
    
    // DNA barcoding queries
    @Query("SELECT q FROM QualityTest q WHERE q.adulterationDetected = true")
    List<QualityTest> findWithAdulterationDetected();
    
    @Query("SELECT q FROM QualityTest q WHERE q.authenticityScore >= :minScore")
    List<QualityTest> findByMinimumAuthenticityScore(@Param("minScore") BigDecimal minScore);
    
    // Retest queries
    List<QualityTest> findByRetestRequiredTrue();
    
    @Query("SELECT q FROM QualityTest q WHERE q.retestRequired = true AND q.testResult = 'PENDING'")
    List<QualityTest> findPendingRetests();
    
    // Search queries
    @Query("SELECT q FROM QualityTest q WHERE " +
           "(LOWER(q.testId) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(q.testName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(q.labName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(q.certificateNumber) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "ORDER BY q.testDate DESC")
    Page<QualityTest> searchQualityTests(@Param("search") String search, Pageable pageable);
    
    // Statistics queries
    @Query("SELECT COUNT(q) FROM QualityTest q WHERE q.tester = :tester")
    Long countByTester(@Param("tester") User tester);
    
    @Query("SELECT q.testResult, COUNT(q) FROM QualityTest q WHERE q.testDate >= :startDate GROUP BY q.testResult")
    List<Object[]> getTestResultStatistics(@Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT q.labName, COUNT(q) FROM QualityTest q WHERE q.testDate >= :startDate GROUP BY q.labName ORDER BY COUNT(q) DESC")
    List<Object[]> getLabStatistics(@Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT q.testType, COUNT(q) FROM QualityTest q WHERE q.testDate >= :startDate GROUP BY q.testType")
    List<Object[]> getTestTypeStatistics(@Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT q.gradeAssigned, COUNT(q) FROM QualityTest q WHERE q.gradeAssigned IS NOT NULL AND q.testDate >= :startDate GROUP BY q.gradeAssigned")
    List<Object[]> getGradeStatistics(@Param("startDate") LocalDateTime startDate);
    
    // Recent activity queries
    @Query("SELECT q FROM QualityTest q ORDER BY q.testDate DESC")
    Page<QualityTest> findRecentTests(Pageable pageable);
    
    @Query("SELECT q FROM QualityTest q WHERE q.createdAt >= :since ORDER BY q.createdAt DESC")
    List<QualityTest> findRecentlyCreated(@Param("since") LocalDateTime since);
    
    // Collection event related queries
    @Query("SELECT q FROM QualityTest q WHERE q.collectionEvent.herbName = :herbName ORDER BY q.testDate DESC")
    List<QualityTest> findByHerbName(@Param("herbName") String herbName);
    
    @Query("SELECT q FROM QualityTest q WHERE q.collectionEvent.collector = :collector ORDER BY q.testDate DESC")
    List<QualityTest> findByCollector(@Param("collector") User collector);
    
    @Query("SELECT q FROM QualityTest q WHERE q.collectionEvent.collectionLocation LIKE %:location% ORDER BY q.testDate DESC")
    List<QualityTest> findByCollectionLocation(@Param("location") String location);
    
    // Compliance queries
    @Query("SELECT q FROM QualityTest q WHERE q.complianceStandards LIKE %:standard%")
    List<QualityTest> findByComplianceStandard(@Param("standard") String standard);
    
    @Query("SELECT q FROM QualityTest q WHERE q.certificateNumber IS NOT NULL ORDER BY q.testDate DESC")
    List<QualityTest> findWithCertificates();
    
    // Monthly statistics for dashboard
    @Query("SELECT YEAR(q.testDate), MONTH(q.testDate), COUNT(q), " +
           "SUM(CASE WHEN q.testResult = 'PASS' THEN 1 ELSE 0 END), " +
           "SUM(CASE WHEN q.testResult = 'FAIL' THEN 1 ELSE 0 END) " +
           "FROM QualityTest q WHERE q.testDate >= :startDate " +
           "GROUP BY YEAR(q.testDate), MONTH(q.testDate) " +
           "ORDER BY YEAR(q.testDate), MONTH(q.testDate)")
    List<Object[]> getMonthlyStatistics(@Param("startDate") LocalDateTime startDate);
    
    // Failed tests requiring action
    @Query("SELECT q FROM QualityTest q WHERE q.testResult = 'FAIL' AND q.retestRequired = false ORDER BY q.testDate DESC")
    List<QualityTest> findFailedTestsWithoutRetest();
    
    // Tests by herb and result
    @Query("SELECT q FROM QualityTest q WHERE q.collectionEvent.herbName = :herbName AND q.testResult = :result ORDER BY q.testDate DESC")
    List<QualityTest> findByHerbNameAndResult(@Param("herbName") String herbName, @Param("result") QualityTest.TestResult result);
    
    // Authenticity verification queries
    @Query("SELECT q FROM QualityTest q WHERE q.dnaBarcode IS NOT NULL AND q.speciesConfirmation IS NOT NULL ORDER BY q.testDate DESC")
    List<QualityTest> findWithDnaVerification();
    
    // Average test scores by herb
    @Query("SELECT q.collectionEvent.herbName, AVG(q.authenticityScore) FROM QualityTest q " +
           "WHERE q.authenticityScore IS NOT NULL GROUP BY q.collectionEvent.herbName")
    List<Object[]> getAverageAuthenticityScoresByHerb();
}
