package com.example.ayutrace.repository;

import com.example.ayutrace.model.CollectionEvent;
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
public interface CollectionEventRepository extends JpaRepository<CollectionEvent, Long> {
    
    // Basic queries
    Optional<CollectionEvent> findByCollectionId(String collectionId);
    List<CollectionEvent> findByCollector(User collector);
    Page<CollectionEvent> findByCollector(User collector, Pageable pageable);
    
    // Herb-based queries
    List<CollectionEvent> findByHerbName(String herbName);
    List<CollectionEvent> findByHerbNameIgnoreCase(String herbName);
    List<CollectionEvent> findByScientificName(String scientificName);
    
    @Query("SELECT DISTINCT c.herbName FROM CollectionEvent c ORDER BY c.herbName")
    List<String> findDistinctHerbNames();
    
    @Query("SELECT DISTINCT c.scientificName FROM CollectionEvent c WHERE c.scientificName IS NOT NULL ORDER BY c.scientificName")
    List<String> findDistinctScientificNames();
    
    // Location-based queries
    List<CollectionEvent> findByCollectionLocation(String collectionLocation);
    List<CollectionEvent> findByCollectionLocationContainingIgnoreCase(String locationKeyword);
    
    @Query("SELECT c FROM CollectionEvent c WHERE " +
           "c.latitude BETWEEN :minLat AND :maxLat AND " +
           "c.longitude BETWEEN :minLng AND :maxLng")
    List<CollectionEvent> findByLocationBounds(
        @Param("minLat") BigDecimal minLatitude,
        @Param("maxLat") BigDecimal maxLatitude,
        @Param("minLng") BigDecimal minLongitude,
        @Param("maxLng") BigDecimal maxLongitude
    );
    
    @Query("SELECT DISTINCT c.collectionLocation FROM CollectionEvent c ORDER BY c.collectionLocation")
    List<String> findDistinctCollectionLocations();
    
    // Date-based queries
    List<CollectionEvent> findByCollectionDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<CollectionEvent> findByCollectionDateAfter(LocalDateTime date);
    List<CollectionEvent> findByCollectionDateBefore(LocalDateTime date);
    
    @Query("SELECT c FROM CollectionEvent c WHERE " +
           "YEAR(c.collectionDate) = :year AND MONTH(c.collectionDate) = :month")
    List<CollectionEvent> findByCollectionMonth(@Param("year") int year, @Param("month") int month);
    
    @Query("SELECT c FROM CollectionEvent c WHERE c.season = :season AND YEAR(c.collectionDate) = :year")
    List<CollectionEvent> findBySeasonAndYear(@Param("season") CollectionEvent.Season season, @Param("year") int year);
    
    // Status-based queries
    List<CollectionEvent> findByStatus(CollectionEvent.CollectionStatus status);
    Page<CollectionEvent> findByStatus(CollectionEvent.CollectionStatus status, Pageable pageable);
    
    @Query("SELECT c FROM CollectionEvent c WHERE c.status IN :statuses ORDER BY c.collectionDate DESC")
    List<CollectionEvent> findByStatusIn(@Param("statuses") List<CollectionEvent.CollectionStatus> statuses);
    
    // Quantity-based queries
    @Query("SELECT c FROM CollectionEvent c WHERE c.quantityKg >= :minQuantity")
    List<CollectionEvent> findByQuantityGreaterThanEqual(@Param("minQuantity") BigDecimal minQuantity);
    
    @Query("SELECT c FROM CollectionEvent c WHERE c.quantityKg BETWEEN :minQuantity AND :maxQuantity")
    List<CollectionEvent> findByQuantityRange(@Param("minQuantity") BigDecimal minQuantity, @Param("maxQuantity") BigDecimal maxQuantity);
    
    // Collection method queries
    List<CollectionEvent> findByCollectionMethod(CollectionEvent.CollectionMethod collectionMethod);
    List<CollectionEvent> findBySeason(CollectionEvent.Season season);
    
    // Search queries
    @Query("SELECT c FROM CollectionEvent c WHERE " +
           "(LOWER(c.herbName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.collectionLocation) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.collectionId) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "ORDER BY c.collectionDate DESC")
    Page<CollectionEvent> searchCollections(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT c FROM CollectionEvent c WHERE " +
           "c.collector = :collector AND " +
           "(LOWER(c.herbName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.collectionLocation) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<CollectionEvent> searchCollectorCollections(@Param("collector") User collector, @Param("search") String search);
    
    // Statistics queries
    @Query("SELECT COUNT(c) FROM CollectionEvent c WHERE c.collector = :collector")
    Long countByCollector(@Param("collector") User collector);
    
    @Query("SELECT SUM(c.quantityKg) FROM CollectionEvent c WHERE c.collector = :collector")
    BigDecimal sumQuantityByCollector(@Param("collector") User collector);
    
    @Query("SELECT c.herbName, SUM(c.quantityKg) FROM CollectionEvent c " +
           "WHERE c.collectionDate >= :startDate GROUP BY c.herbName ORDER BY SUM(c.quantityKg) DESC")
    List<Object[]> getHerbQuantityStatistics(@Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT c.collectionLocation, COUNT(c) FROM CollectionEvent c " +
           "WHERE c.collectionDate >= :startDate GROUP BY c.collectionLocation ORDER BY COUNT(c) DESC")
    List<Object[]> getLocationStatistics(@Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT c.season, COUNT(c) FROM CollectionEvent c " +
           "WHERE c.collectionDate >= :startDate GROUP BY c.season")
    List<Object[]> getSeasonStatistics(@Param("startDate") LocalDateTime startDate);
    
    // Recent activity queries
    @Query("SELECT c FROM CollectionEvent c ORDER BY c.collectionDate DESC")
    Page<CollectionEvent> findRecentCollections(Pageable pageable);
    
    @Query("SELECT c FROM CollectionEvent c WHERE c.createdAt >= :since ORDER BY c.createdAt DESC")
    List<CollectionEvent> findRecentlyCreated(@Param("since") LocalDateTime since);
    
    @Query("SELECT c FROM CollectionEvent c WHERE c.updatedAt >= :since AND c.updatedAt > c.createdAt ORDER BY c.updatedAt DESC")
    List<CollectionEvent> findRecentlyUpdated(@Param("since") LocalDateTime since);
    
    // Monthly statistics for dashboard
    @Query("SELECT YEAR(c.collectionDate), MONTH(c.collectionDate), COUNT(c), SUM(c.quantityKg) " +
           "FROM CollectionEvent c WHERE c.collectionDate >= :startDate " +
           "GROUP BY YEAR(c.collectionDate), MONTH(c.collectionDate) " +
           "ORDER BY YEAR(c.collectionDate), MONTH(c.collectionDate)")
    List<Object[]> getMonthlyStatistics(@Param("startDate") LocalDateTime startDate);
    
    // Collections ready for processing
    @Query("SELECT c FROM CollectionEvent c WHERE c.status = 'COLLECTED' OR c.status = 'IN_STORAGE' ORDER BY c.collectionDate")
    List<CollectionEvent> findReadyForProcessing();
    
    // Collections by collector and date range
    @Query("SELECT c FROM CollectionEvent c WHERE c.collector = :collector " +
           "AND c.collectionDate BETWEEN :startDate AND :endDate " +
           "ORDER BY c.collectionDate DESC")
    List<CollectionEvent> findByCollectorAndDateRange(
        @Param("collector") User collector,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
    
    // Quality control queries
    @Query("SELECT c FROM CollectionEvent c LEFT JOIN c.qualityTests q " +
           "WHERE q.id IS NULL AND c.status != 'REJECTED' " +
           "ORDER BY c.collectionDate")
    List<CollectionEvent> findWithoutQualityTests();
    
    @Query("SELECT c FROM CollectionEvent c JOIN c.qualityTests q " +
           "WHERE q.testResult = 'PASS' AND c.status = 'TESTED' " +
           "ORDER BY c.collectionDate DESC")
    List<CollectionEvent> findWithPassedQualityTests();
    
    // Additional methods for basic frontend support
    List<CollectionEvent> findByCollectorOrderByCollectionDateDesc(User collector);
    
    @Query("SELECT c FROM CollectionEvent c ORDER BY c.collectionDate DESC")
    List<CollectionEvent> findTop50ByOrderByCollectionDateDesc();
}
