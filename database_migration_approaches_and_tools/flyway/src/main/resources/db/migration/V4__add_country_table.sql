-- db/migration/V4__add_country_table.sql

-- Create the country table
CREATE TABLE country (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    country_code VARCHAR(255) NOT NULL UNIQUE
);

-- Add a country_id column to the company table and set up a foreign key relationship
ALTER TABLE company
ADD COLUMN country_id BIGINT,
ADD CONSTRAINT fk_company_country
    FOREIGN KEY (country_id)
    REFERENCES country(id);

-- Add a country_id column to the employee table and set up a foreign key relationship
ALTER TABLE employee
ADD COLUMN country_id BIGINT,
ADD CONSTRAINT fk_employee_country
    FOREIGN KEY (country_id)
    REFERENCES country(id);

-- Optional: Drop the existing country_code column from the company table (if no longer needed)
ALTER TABLE company
DROP COLUMN country_code;
