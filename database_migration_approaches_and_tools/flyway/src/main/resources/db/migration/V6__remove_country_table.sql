-- db/migration/V6__remove_country_table.sql

-- Drop foreign key constraints referencing country table
ALTER TABLE company
DROP FOREIGN KEY fk_company_country;

ALTER TABLE employee
DROP FOREIGN KEY fk_employee_country;

-- Drop country_id column from company table
ALTER TABLE company
DROP COLUMN country_id;

-- Drop country_id column from employee table
ALTER TABLE employee
DROP COLUMN country_id;

-- Drop the country table
DROP TABLE country;
