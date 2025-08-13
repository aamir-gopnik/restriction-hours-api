package com.example.restrictionhoursapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "restriction_hours")
public class RestrictionHour {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(name = "store_id", nullable = false)
    private Integer storeId;
    
    @NotBlank
    @Column(name = "day_of_week", nullable = false)
    private String dayOfWeek;
    
    @NotBlank
    @Column(name = "category", nullable = false)
    private String category;
    
    @NotNull
    @Column(name = "category_code", nullable = false)
    private Integer categoryCode;
    
    @NotBlank
    @Column(name = "category_desc", nullable = false)
    private String categoryDesc;
    
    @NotNull
    @Column(name = "has_restriction_hour", nullable = false)
    private Boolean hasRestrictionHour;
    
    @Min(0)
    @Max(23)
    @Column(name = "start_hour")
    private Integer startHour;
    
    @Min(0)
    @Max(23)
    @Column(name = "end_hour")
    private Integer endHour;
    
    // Default constructor
    public RestrictionHour() {}
    
    // Constructor with all fields except id
    public RestrictionHour(Integer storeId, String dayOfWeek, String category, 
                          Integer categoryCode, String categoryDesc, 
                          Boolean hasRestrictionHour, Integer startHour, Integer endHour) {
        this.storeId = storeId;
        this.dayOfWeek = dayOfWeek;
        this.category = category;
        this.categoryCode = categoryCode;
        this.categoryDesc = categoryDesc;
        this.hasRestrictionHour = hasRestrictionHour;
        this.startHour = startHour;
        this.endHour = endHour;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getStoreId() {
        return storeId;
    }
    
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
    
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public Integer getCategoryCode() {
        return categoryCode;
    }
    
    public void setCategoryCode(Integer categoryCode) {
        this.categoryCode = categoryCode;
    }
    
    public String getCategoryDesc() {
        return categoryDesc;
    }
    
    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }
    
    public Boolean getHasRestrictionHour() {
        return hasRestrictionHour;
    }
    
    public void setHasRestrictionHour(Boolean hasRestrictionHour) {
        this.hasRestrictionHour = hasRestrictionHour;
    }
    
    public Integer getStartHour() {
        return startHour;
    }
    
    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }
    
    public Integer getEndHour() {
        return endHour;
    }
    
    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }
    
    @Override
    public String toString() {
        return "RestrictionHour{" +
                "id=" + id +
                ", storeId=" + storeId +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", category='" + category + '\'' +
                ", categoryCode=" + categoryCode +
                ", categoryDesc='" + categoryDesc + '\'' +
                ", hasRestrictionHour=" + hasRestrictionHour +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                '}';
    }
} 