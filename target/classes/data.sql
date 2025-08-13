-- Sample data for restriction_hours table
INSERT INTO restriction_hours (store_id, day_of_week, category, category_code, category_desc, has_restriction_hour, start_hour, end_hour) VALUES
-- Store 1 - BEER category (code 101)
(1, 'Monday', 'ALCOHOL', 101, 'BEER', true, 6, 12),
(1, 'Tuesday', 'ALCOHOL', 101, 'BEER', true, 6, 12),
(1, 'Wednesday', 'ALCOHOL', 101, 'BEER', true, 6, 12),
(1, 'Thursday', 'ALCOHOL', 101, 'BEER', true, 6, 12),
(1, 'Friday', 'ALCOHOL', 101, 'BEER', true, 6, 12),
(1, 'Saturday', 'ALCOHOL', 101, 'BEER', true, 6, 12),
(1, 'Sunday', 'ALCOHOL', 101, 'BEER', true, 6, 12),

-- Store 2 - LIQUOR category (code 102)
(2, 'Monday', 'ALCOHOL', 102, 'LIQUOR', true, 6, 12),
(2, 'Tuesday', 'ALCOHOL', 102, 'LIQUOR', true, 6, 12),
(2, 'Wednesday', 'ALCOHOL', 102, 'LIQUOR', true, 6, 12),
(2, 'Thursday', 'ALCOHOL', 102, 'LIQUOR', true, 6, 12),
(2, 'Friday', 'ALCOHOL', 102, 'LIQUOR', true, 6, 12),
(2, 'Saturday', 'ALCOHOL', 102, 'LIQUOR', true, 6, 12),
(2, 'Sunday', 'ALCOHOL', 102, 'LIQUOR', true, 6, 12),

-- Store 3 - Single Serve Beer category (code 103)
(3, 'Monday', 'ALCOHOL', 103, 'Single Serve Beer', true, 6, 12),
(3, 'Tuesday', 'ALCOHOL', 103, 'Single Serve Beer', true, 6, 12),
(3, 'Wednesday', 'ALCOHOL', 103, 'Single Serve Beer', true, 6, 12),
(3, 'Thursday', 'ALCOHOL', 103, 'Single Serve Beer', true, 6, 12),
(3, 'Friday', 'ALCOHOL', 103, 'Single Serve Beer', true, 6, 12),
(3, 'Saturday', 'ALCOHOL', 103, 'Single Serve Beer', true, 6, 12),
(3, 'Sunday', 'ALCOHOL', 103, 'Single Serve Beer', true, 6, 12),

-- Store 4 - Wine category (code 104)
(4, 'Monday', 'ALCOHOL', 104, 'Wine', true, 6, 12),
(4, 'Tuesday', 'ALCOHOL', 104, 'Wine', true, 6, 12),
(4, 'Wednesday', 'ALCOHOL', 104, 'Wine', true, 6, 12),
(4, 'Thursday', 'ALCOHOL', 104, 'Wine', true, 6, 12),
(4, 'Friday', 'ALCOHOL', 104, 'Wine', true, 6, 12),
(4, 'Saturday', 'ALCOHOL', 104, 'Wine', true, 6, 12),
(4, 'Sunday', 'ALCOHOL', 104, 'Wine', true, 6, 12),

-- Store 5 - BEER category with different hours
(5, 'Monday', 'ALCOHOL', 101, 'BEER', true, 8, 14),
(5, 'Tuesday', 'ALCOHOL', 101, 'BEER', true, 8, 14),
(5, 'Wednesday', 'ALCOHOL', 101, 'BEER', false, 0, 0),
(5, 'Thursday', 'ALCOHOL', 101, 'BEER', true, 8, 14),
(5, 'Friday', 'ALCOHOL', 101, 'BEER', true, 8, 14),
(5, 'Saturday', 'ALCOHOL', 101, 'BEER', true, 8, 14),
(5, 'Sunday', 'ALCOHOL', 101, 'BEER', false, 0, 0); 