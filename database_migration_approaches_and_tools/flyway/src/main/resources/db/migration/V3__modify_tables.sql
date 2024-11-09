-- db/migration/V3__modify_tables.sql

-- Add a new column to the company table
ALTER TABLE company
ADD COLUMN industry VARCHAR(255);

-- Remove the lastName column from the employee table
ALTER TABLE employee
DROP COLUMN last_name;
