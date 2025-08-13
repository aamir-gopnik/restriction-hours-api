-- Schema definition for RESTRICTION_HOURS table
-- This file is automatically executed on Spring Boot startup before data.sql

DROP TABLE IF EXISTS restriction_hours;

CREATE TABLE restriction_hours (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    store_id INTEGER NOT NULL,
    day_of_week VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    category_code INTEGER NOT NULL,
    category_desc VARCHAR(255) NOT NULL,
    has_restriction_hour BOOLEAN NOT NULL,
    start_hour INTEGER CHECK (start_hour >= 0 AND start_hour <= 23),
    end_hour INTEGER CHECK (end_hour >= 0 AND end_hour <= 23)
);

-- Create indexes for better query performance
CREATE INDEX idx_restriction_hours_store_id ON restriction_hours(store_id);
CREATE INDEX idx_restriction_hours_category ON restriction_hours(category);
CREATE INDEX idx_restriction_hours_day ON restriction_hours(day_of_week);
CREATE INDEX idx_restriction_hours_store_day_category ON restriction_hours(store_id, day_of_week, category);