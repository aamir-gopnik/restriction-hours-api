package com.example.restrictionhoursapi.service;

import com.example.restrictionhoursapi.entity.RestrictionHour;
import com.example.restrictionhoursapi.repository.RestrictionHourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestrictionHourService {
    
    @Autowired
    private RestrictionHourRepository restrictionHourRepository;
    
    // GET operations
    
    /**
     * Get all restriction hours
     */
    public List<RestrictionHour> getAllRestrictionHours() {
        return restrictionHourRepository.findAll();
    }
    
    /**
     * Get restriction hour by ID
     */
    public Optional<RestrictionHour> getRestrictionHourById(Long id) {
        return restrictionHourRepository.findById(id);
    }
    
    /**
     * Get restriction hours by store ID
     */
    public List<RestrictionHour> getRestrictionHoursByStoreId(Integer storeId) {
        return restrictionHourRepository.findByStoreId(storeId);
    }
    
    /**
     * Get restriction hours by store ID and day of week
     */
    public List<RestrictionHour> getRestrictionHoursByStoreIdAndDay(Integer storeId, String dayOfWeek) {
        return restrictionHourRepository.findByStoreIdAndDayOfWeek(storeId, dayOfWeek);
    }
    
    /**
     * Get restriction hours by day of week
     */
    public List<RestrictionHour> getRestrictionHoursByDay(String dayOfWeek) {
        return restrictionHourRepository.findByDayOfWeek(dayOfWeek);
    }
    
    /**
     * Get restriction hours by category code
     */
    public List<RestrictionHour> getRestrictionHoursByCategoryCode(Integer categoryCode) {
        return restrictionHourRepository.findByCategoryCode(categoryCode);
    }
    
    /**
     * Get restriction hours that have restrictions enabled
     */
    public List<RestrictionHour> getRestrictionHoursWithRestrictions() {
        return restrictionHourRepository.findByHasRestrictionHourTrue();
    }
    
    /**
     * Get restriction hours by multiple store IDs
     */
    public List<RestrictionHour> getRestrictionHoursByStoreIds(List<Integer> storeIds) {
        return restrictionHourRepository.findByStoreIdIn(storeIds);
    }
    
    // POST operations
    
    /**
     * Create a new restriction hour
     */
    public RestrictionHour createRestrictionHour(RestrictionHour restrictionHour) {
        // Validate the restriction hour data
        validateRestrictionHour(restrictionHour);
        
        // Check if restriction hour already exists for this store and day
        if (restrictionHourRepository.existsByStoreIdAndDayOfWeek(
                restrictionHour.getStoreId(), restrictionHour.getDayOfWeek())) {
            throw new IllegalArgumentException(
                "Restriction hour already exists for store " + restrictionHour.getStoreId() + 
                " on " + restrictionHour.getDayOfWeek());
        }
        
        return restrictionHourRepository.save(restrictionHour);
    }
    
    /**
     * Create multiple restriction hours
     */
    public List<RestrictionHour> createRestrictionHours(List<RestrictionHour> restrictionHours) {
        // Validate each restriction hour
        for (RestrictionHour restrictionHour : restrictionHours) {
            validateRestrictionHour(restrictionHour);
        }
        
        // Check for duplicates within the list
        for (int i = 0; i < restrictionHours.size(); i++) {
            for (int j = i + 1; j < restrictionHours.size(); j++) {
                RestrictionHour rh1 = restrictionHours.get(i);
                RestrictionHour rh2 = restrictionHours.get(j);
                if (rh1.getStoreId().equals(rh2.getStoreId()) && 
                    rh1.getDayOfWeek().equals(rh2.getDayOfWeek())) {
                    throw new IllegalArgumentException(
                        "Duplicate restriction hour found for store " + rh1.getStoreId() + 
                        " on " + rh1.getDayOfWeek());
                }
            }
        }
        
        // Check for existing entries in database
        for (RestrictionHour restrictionHour : restrictionHours) {
            if (restrictionHourRepository.existsByStoreIdAndDayOfWeek(
                    restrictionHour.getStoreId(), restrictionHour.getDayOfWeek())) {
                throw new IllegalArgumentException(
                    "Restriction hour already exists for store " + restrictionHour.getStoreId() + 
                    " on " + restrictionHour.getDayOfWeek());
            }
        }
        
        return restrictionHourRepository.saveAll(restrictionHours);
    }
    
    // PUT operations
    
    /**
     * Update restriction hour by composite key (storeId, dayOfWeek, category, categoryCode)
     */
    public RestrictionHour updateRestrictionHour(Integer storeId, String dayOfWeek, 
                                               String category, Integer categoryCode, 
                                               RestrictionHour updatedRestrictionHour) {
        // Find existing restriction hour by composite key
        RestrictionHour existingRestrictionHour = restrictionHourRepository
            .findByStoreIdAndDayOfWeekAndCategoryAndCategoryCode(storeId, dayOfWeek, category, categoryCode);
        
        if (existingRestrictionHour == null) {
            throw new IllegalArgumentException(
                "Restriction hour not found for Store ID: " + storeId + 
                ", Day: " + dayOfWeek + 
                ", Category: " + category + 
                ", Category Code: " + categoryCode);
        }
        
        // Validate the updated data
        validateRestrictionHour(updatedRestrictionHour);
        
        // Update the fields (keep the same ID and composite key fields)
        existingRestrictionHour.setHasRestrictionHour(updatedRestrictionHour.getHasRestrictionHour());
        existingRestrictionHour.setStartHour(updatedRestrictionHour.getStartHour());
        existingRestrictionHour.setEndHour(updatedRestrictionHour.getEndHour());
        existingRestrictionHour.setCategoryDesc(updatedRestrictionHour.getCategoryDesc());
        
        return restrictionHourRepository.save(existingRestrictionHour);
    }
    
    /**
     * Update restriction hour by entity with composite key validation
     */
    public RestrictionHour updateRestrictionHour(RestrictionHour restrictionHour) {
        if (restrictionHour.getStoreId() == null || restrictionHour.getDayOfWeek() == null ||
            restrictionHour.getCategory() == null || restrictionHour.getCategoryCode() == null) {
            throw new IllegalArgumentException("All composite key fields (storeId, dayOfWeek, category, categoryCode) must be provided");
        }
        
        return updateRestrictionHour(
            restrictionHour.getStoreId(),
            restrictionHour.getDayOfWeek(),
            restrictionHour.getCategory(),
            restrictionHour.getCategoryCode(),
            restrictionHour
        );
    }
    
    // DELETE operations
    
    /**
     * Delete restriction hour by composite key (storeId, dayOfWeek, category, categoryCode)
     */
    public boolean deleteRestrictionHour(Integer storeId, String dayOfWeek, 
                                       String category, Integer categoryCode) {
        // Find existing restriction hour by composite key
        RestrictionHour existingRestrictionHour = restrictionHourRepository
            .findByStoreIdAndDayOfWeekAndCategoryAndCategoryCode(storeId, dayOfWeek, category, categoryCode);
        
        if (existingRestrictionHour == null) {
            throw new IllegalArgumentException(
                "Restriction hour not found for Store ID: " + storeId + 
                ", Day: " + dayOfWeek + 
                ", Category: " + category + 
                ", Category Code: " + categoryCode);
        }
        
        restrictionHourRepository.delete(existingRestrictionHour);
        return true;
    }
    
    /**
     * Delete restriction hour by entity with composite key validation
     */
    public boolean deleteRestrictionHour(RestrictionHour restrictionHour) {
        if (restrictionHour.getStoreId() == null || restrictionHour.getDayOfWeek() == null ||
            restrictionHour.getCategory() == null || restrictionHour.getCategoryCode() == null) {
            throw new IllegalArgumentException("All composite key fields (storeId, dayOfWeek, category, categoryCode) must be provided");
        }
        
        return deleteRestrictionHour(
            restrictionHour.getStoreId(),
            restrictionHour.getDayOfWeek(),
            restrictionHour.getCategory(),
            restrictionHour.getCategoryCode()
        );
    }
    
    /**
     * Delete multiple restriction hours by composite keys
     */
    public int deleteRestrictionHours(List<RestrictionHour> restrictionHours) {
        if (restrictionHours == null || restrictionHours.isEmpty()) {
            throw new IllegalArgumentException("Restriction hours list cannot be empty");
        }
        
        int deletedCount = 0;
        for (RestrictionHour restrictionHour : restrictionHours) {
            try {
                deleteRestrictionHour(restrictionHour);
                deletedCount++;
            } catch (IllegalArgumentException e) {
                // Log the error but continue with other deletions
                System.err.println("Failed to delete restriction hour: " + e.getMessage());
            }
        }
        
        return deletedCount;
    }
    
    // Helper methods
    
    /**
     * Validate restriction hour data
     */
    private void validateRestrictionHour(RestrictionHour restrictionHour) {
        if (restrictionHour.getStoreId() == null || restrictionHour.getStoreId() <= 0) {
            throw new IllegalArgumentException("Store ID must be a positive number");
        }
        
        if (restrictionHour.getDayOfWeek() == null || restrictionHour.getDayOfWeek().trim().isEmpty()) {
            throw new IllegalArgumentException("Day of week cannot be empty");
        }
        
        // Validate day of week
        String[] validDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        boolean validDay = false;
        for (String day : validDays) {
            if (day.equalsIgnoreCase(restrictionHour.getDayOfWeek().trim())) {
                restrictionHour.setDayOfWeek(day); // Set to proper case
                validDay = true;
                break;
            }
        }
        if (!validDay) {
            throw new IllegalArgumentException("Invalid day of week: " + restrictionHour.getDayOfWeek());
        }
        
        if (restrictionHour.getCategory() == null || restrictionHour.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty");
        }
        
        if (restrictionHour.getCategoryCode() == null) {
            throw new IllegalArgumentException("Category code cannot be null");
        }
        
        if (restrictionHour.getCategoryDesc() == null || restrictionHour.getCategoryDesc().trim().isEmpty()) {
            throw new IllegalArgumentException("Category description cannot be empty");
        }
        
        if (restrictionHour.getHasRestrictionHour() == null) {
            throw new IllegalArgumentException("Has restriction hour cannot be null");
        }
        
        // Validate hours if restriction is enabled
        if (restrictionHour.getHasRestrictionHour()) {
            if (restrictionHour.getStartHour() == null || 
                restrictionHour.getStartHour() < 0 || restrictionHour.getStartHour() > 23) {
                throw new IllegalArgumentException("Start hour must be between 0 and 23");
            }
            
            if (restrictionHour.getEndHour() == null || 
                restrictionHour.getEndHour() < 0 || restrictionHour.getEndHour() > 23) {
                throw new IllegalArgumentException("End hour must be between 0 and 23");
            }
        }
    }
} 