-- db/migration/V5__add_new_data.sql

-- Insert new data into the country table
INSERT INTO country (country_code) VALUES
('IN'), -- India
('JP'), -- Japan
('CA'), -- Canada
('GB'); -- United Kingdom

-- Insert new data into the company table and link with country
INSERT INTO company (company_name, country_id, industry) VALUES
('EcoWare', (SELECT id FROM country WHERE country_code = 'IN'), 'Environmental Solutions'),
('NexGenTech', (SELECT id FROM country WHERE country_code = 'JP'), 'Technology Solutions'),
('MapleCorp', (SELECT id FROM country WHERE country_code = 'CA'), 'Consulting'),
('BritIndust', (SELECT id FROM country WHERE country_code = 'GB'), 'Manufacturing');

-- Insert new data into the employee table and link with country
INSERT INTO employee (employee_id, email, first_name, country_id) VALUES
('EMP005', 'rahul.sharma@ecoware.in', 'Rahul', (SELECT id FROM country WHERE country_code = 'IN')),
('EMP006', 'yuki.tanaka@nexgentech.jp', 'Yuki', (SELECT id FROM country WHERE country_code = 'JP')),
('EMP007', 'liam.brown@maplecorp.ca', 'Liam', (SELECT id FROM country WHERE country_code = 'CA')),
('EMP008', 'olivia.wilson@britindust.gb', 'Olivia', (SELECT id FROM country WHERE country_code = 'GB'));