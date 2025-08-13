package com.example.restrictionhoursapi.controller;

import com.example.restrictionhoursapi.entity.RestrictionHour;
import com.example.restrictionhoursapi.service.RestrictionHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restriction-hours")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class RestrictionHourController {
    
    @Autowired
    private RestrictionHourService restrictionHourService;
    
    // GET endpoints
    
    /**
     * Get all restriction hours
     * GET /api/restriction-hours
     */
    @GetMapping("/getAllRestrictionHours")
    public ResponseEntity<List<RestrictionHour>> getAllRestrictionHours() {
        try {
            List<RestrictionHour> restrictionHours = restrictionHourService.getAllRestrictionHours();
            return ResponseEntity.ok(restrictionHours);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Get restriction hour by ID
     * GET /api/restriction-hours/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<RestrictionHour> getRestrictionHourById(@PathVariable Long id) {
        try {
            Optional<RestrictionHour> restrictionHour = restrictionHourService.getRestrictionHourById(id);
            return restrictionHour.map(ResponseEntity::ok)
                                 .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Get restriction hours by store ID
     * GET /api/restriction-hours/store/{storeId}
     */
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<RestrictionHour>> getRestrictionHoursByStoreId(@PathVariable Integer storeId) {
        try {
            List<RestrictionHour> restrictionHours = restrictionHourService.getRestrictionHoursByStoreId(storeId);
            return ResponseEntity.ok(restrictionHours);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Get restriction hours by store ID and day of week
     * GET /api/restriction-hours/store/{storeId}/day/{dayOfWeek}
     */
    @GetMapping("/store/{storeId}/day/{dayOfWeek}")
    public ResponseEntity<List<RestrictionHour>> getRestrictionHoursByStoreIdAndDay(
            @PathVariable Integer storeId, @PathVariable String dayOfWeek) {
        try {
            List<RestrictionHour> restrictionHours = 
                restrictionHourService.getRestrictionHoursByStoreIdAndDay(storeId, dayOfWeek);
            return ResponseEntity.ok(restrictionHours);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Get restriction hours by day of week
     * GET /api/restriction-hours/day/{dayOfWeek}
     */
    @GetMapping("/day/{dayOfWeek}")
    public ResponseEntity<List<RestrictionHour>> getRestrictionHoursByDay(@PathVariable String dayOfWeek) {
        try {
            List<RestrictionHour> restrictionHours = restrictionHourService.getRestrictionHoursByDay(dayOfWeek);
            return ResponseEntity.ok(restrictionHours);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Get restriction hours by category code
     * GET /api/restriction-hours/category/{categoryCode}
     */
    @GetMapping("/category/{categoryCode}")
    public ResponseEntity<List<RestrictionHour>> getRestrictionHoursByCategoryCode(@PathVariable Integer categoryCode) {
        try {
            List<RestrictionHour> restrictionHours = 
                restrictionHourService.getRestrictionHoursByCategoryCode(categoryCode);
            return ResponseEntity.ok(restrictionHours);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Get restriction hours that have restrictions enabled
     * GET /api/restriction-hours/with-restrictions
     */
    @GetMapping("/with-restrictions")
    public ResponseEntity<List<RestrictionHour>> getRestrictionHoursWithRestrictions() {
        try {
            List<RestrictionHour> restrictionHours = restrictionHourService.getRestrictionHoursWithRestrictions();
            return ResponseEntity.ok(restrictionHours);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Get restriction hours by multiple store IDs (comma-separated)
     * GET /api/restriction-hours/stores?ids=1,2,3
     */
    @GetMapping("/stores")
    public ResponseEntity<List<RestrictionHour>> getRestrictionHoursByStoreIds(
            @RequestParam("ids") List<Integer> storeIds) {
        try {
            List<RestrictionHour> restrictionHours = 
                restrictionHourService.getRestrictionHoursByStoreIds(storeIds);
            return ResponseEntity.ok(restrictionHours);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // POST endpoints
    
    /**
     * Create a new restriction hour
     * POST /api/restriction-hours
     */
    @PostMapping("/addRestrictionHour")
    public ResponseEntity<?> createRestrictionHour(@Valid @RequestBody RestrictionHour restrictionHour) {
        try {
            RestrictionHour createdRestrictionHour = restrictionHourService.createRestrictionHour(restrictionHour);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRestrictionHour);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ErrorResponse("An error occurred while creating the restriction hour"));
        }
    }
    
    /**
     * Create multiple restriction hours
     * POST /api/restriction-hours/batch
     */
    @PostMapping("/batch")
    public ResponseEntity<?> createRestrictionHours(@Valid @RequestBody List<RestrictionHour> restrictionHours) {
        try {
            if (restrictionHours == null || restrictionHours.isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Restriction hours list cannot be empty"));
            }
            
            List<RestrictionHour> createdRestrictionHours = 
                restrictionHourService.createRestrictionHours(restrictionHours);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRestrictionHours);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ErrorResponse("An error occurred while creating the restriction hours"));
        }
    }
    
    // PUT endpoints
    
    /**
     * Update restriction hour by composite key
     * PUT /api/restriction-hours/store/{storeId}/day/{dayOfWeek}/category/{category}/code/{categoryCode}
     */
    @PutMapping("/store/{storeId}/day/{dayOfWeek}/category/{category}/code/{categoryCode}")
    public ResponseEntity<?> updateRestrictionHour(
            @PathVariable Integer storeId,
            @PathVariable String dayOfWeek,
            @PathVariable String category,
            @PathVariable Integer categoryCode,
            @Valid @RequestBody RestrictionHour restrictionHour) {
        try {
            RestrictionHour updatedRestrictionHour = restrictionHourService.updateRestrictionHour(
                storeId, dayOfWeek, category, categoryCode, restrictionHour);
            return ResponseEntity.ok(updatedRestrictionHour);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ErrorResponse("An error occurred while updating the restriction hour"));
        }
    }
    
    /**
     * Update restriction hour using request body for composite key
     * PUT /api/restriction-hours/update
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateRestrictionHour(@Valid @RequestBody RestrictionHour restrictionHour) {
        try {
            RestrictionHour updatedRestrictionHour = restrictionHourService.updateRestrictionHour(restrictionHour);
            return ResponseEntity.ok(updatedRestrictionHour);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ErrorResponse("An error occurred while updating the restriction hour"));
        }
    }
    
    /**
     * Batch update multiple restriction hours
     * PUT /api/restriction-hours/batch-update
     */
    @PutMapping("/batch-update")
    public ResponseEntity<?> updateRestrictionHours(@Valid @RequestBody List<RestrictionHour> restrictionHours) {
        try {
            if (restrictionHours == null || restrictionHours.isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Restriction hours list cannot be empty"));
            }
            
            List<RestrictionHour> updatedRestrictionHours = restrictionHours.stream()
                .map(rh -> restrictionHourService.updateRestrictionHour(rh))
                .collect(Collectors.toList());
                
            return ResponseEntity.ok(updatedRestrictionHours);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ErrorResponse("An error occurred while updating the restriction hours"));
        }
    }
    
    // DELETE endpoints
    
    /**
     * Delete restriction hour by composite key
     * DELETE /api/restriction-hours/store/{storeId}/day/{dayOfWeek}/category/{category}/code/{categoryCode}
     */
    @DeleteMapping("/store/{storeId}/day/{dayOfWeek}/category/{category}/code/{categoryCode}")
    public ResponseEntity<?> deleteRestrictionHour(
            @PathVariable Integer storeId,
            @PathVariable String dayOfWeek,
            @PathVariable String category,
            @PathVariable Integer categoryCode) {
        try {
            boolean deleted = restrictionHourService.deleteRestrictionHour(storeId, dayOfWeek, category, categoryCode);
            if (deleted) {
                return ResponseEntity.ok().body(new SuccessResponse("Restriction hour deleted successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body(new ErrorResponse("Failed to delete restriction hour"));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ErrorResponse("An error occurred while deleting the restriction hour"));
        }
    }
    
    /**
     * Delete restriction hour using request body for composite key
     * DELETE /api/restriction-hours/delete
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteRestrictionHour(@RequestBody RestrictionHour restrictionHour) {
        try {
            boolean deleted = restrictionHourService.deleteRestrictionHour(restrictionHour);
            if (deleted) {
                return ResponseEntity.ok().body(new SuccessResponse("Restriction hour deleted successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body(new ErrorResponse("Failed to delete restriction hour"));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ErrorResponse("An error occurred while deleting the restriction hour"));
        }
    }
    
    /**
     * Batch delete multiple restriction hours
     * DELETE /api/restriction-hours/batch-delete
     */
    @DeleteMapping("/batch-delete")
    public ResponseEntity<?> deleteRestrictionHours(@RequestBody List<RestrictionHour> restrictionHours) {
        try {
            if (restrictionHours == null || restrictionHours.isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Restriction hours list cannot be empty"));
            }
            
            int deletedCount = restrictionHourService.deleteRestrictionHours(restrictionHours);
            return ResponseEntity.ok().body(new BatchDeleteResponse(
                "Batch delete completed", deletedCount, restrictionHours.size()));
                
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ErrorResponse("An error occurred while deleting the restriction hours"));
        }
    }
    
    // Response classes
    public static class SuccessResponse {
        private String message;
        private long timestamp;
        
        public SuccessResponse(String message) {
            this.message = message;
            this.timestamp = System.currentTimeMillis();
        }
        
        // Getters
        public String getMessage() {
            return message;
        }
        
        public long getTimestamp() {
            return timestamp;
        }
        
        // Setters
        public void setMessage(String message) {
            this.message = message;
        }
        
        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }
    
    public static class BatchDeleteResponse {
        private String message;
        private int deletedCount;
        private int totalRequested;
        private long timestamp;
        
        public BatchDeleteResponse(String message, int deletedCount, int totalRequested) {
            this.message = message;
            this.deletedCount = deletedCount;
            this.totalRequested = totalRequested;
            this.timestamp = System.currentTimeMillis();
        }
        
        // Getters
        public String getMessage() {
            return message;
        }
        
        public int getDeletedCount() {
            return deletedCount;
        }
        
        public int getTotalRequested() {
            return totalRequested;
        }
        
        public long getTimestamp() {
            return timestamp;
        }
        
        // Setters
        public void setMessage(String message) {
            this.message = message;
        }
        
        public void setDeletedCount(int deletedCount) {
            this.deletedCount = deletedCount;
        }
        
        public void setTotalRequested(int totalRequested) {
            this.totalRequested = totalRequested;
        }
        
        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }
    
    // Error response class
    public static class ErrorResponse {
        private String message;
        private long timestamp;
        
        public ErrorResponse(String message) {
            this.message = message;
            this.timestamp = System.currentTimeMillis();
        }
        
        // Getters
        public String getMessage() {
            return message;
        }
        
        public long getTimestamp() {
            return timestamp;
        }
        
        // Setters
        public void setMessage(String message) {
            this.message = message;
        }
        
        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }
} 