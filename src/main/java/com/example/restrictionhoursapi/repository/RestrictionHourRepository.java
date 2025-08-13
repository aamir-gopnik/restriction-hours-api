package com.example.restrictionhoursapi.repository;

import com.example.restrictionhoursapi.entity.RestrictionHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestrictionHourRepository extends JpaRepository<RestrictionHour, Long> {
    
    // Find all restriction hours for a specific store
    List<RestrictionHour> findByStoreId(Integer storeId);
    
    // Find restriction hours by store ID and day of week
    List<RestrictionHour> findByStoreIdAndDayOfWeek(Integer storeId, String dayOfWeek);
    
    // Find restriction hours by day of week
    List<RestrictionHour> findByDayOfWeek(String dayOfWeek);
    
    // Find restriction hours by category code
    List<RestrictionHour> findByCategoryCode(Integer categoryCode);
    
    // Find restriction hours that have restrictions enabled
    List<RestrictionHour> findByHasRestrictionHourTrue();
    
    // Find restriction hours that don't have restrictions
    List<RestrictionHour> findByHasRestrictionHourFalse();
    
    // Custom query to find restriction hours by multiple store IDs
    @Query("SELECT rh FROM RestrictionHour rh WHERE rh.storeId IN :storeIds")
    List<RestrictionHour> findByStoreIdIn(@Param("storeIds") List<Integer> storeIds);
    
    // Find restriction hours within a time range
    @Query("SELECT rh FROM RestrictionHour rh WHERE rh.startHour >= :startHour AND rh.endHour <= :endHour")
    List<RestrictionHour> findByTimeRange(@Param("startHour") Integer startHour, @Param("endHour") Integer endHour);
    
    // Check if a restriction hour already exists for a store and day
    boolean existsByStoreIdAndDayOfWeek(Integer storeId, String dayOfWeek);
    
    // Find by composite key: storeId, dayOfWeek, category, categoryCode
    RestrictionHour findByStoreIdAndDayOfWeekAndCategoryAndCategoryCode(
        Integer storeId, String dayOfWeek, String category, Integer categoryCode);
    
    // Check if exists by composite key
    boolean existsByStoreIdAndDayOfWeekAndCategoryAndCategoryCode(
        Integer storeId, String dayOfWeek, String category, Integer categoryCode);
} 