-- db/migration/V2__insert_init_data.sql

-- Insert data into the company table
INSERT INTO company (company_name, country_code) VALUES
('TechCorp', 'US'),
('Innovatech', 'SG'),
('FutureWorks', 'DE'),
('SkyNet', 'AU');

-- Insert data into the employee table
INSERT INTO employee (employee_id, email, first_name, last_name) VALUES
('EMP001', 'john.doe@techcorp.com', 'John', 'Doe'),
('EMP002', 'jane.smith@innovatech.sg', 'Jane', 'Smith'),
('EMP003', 'mike.johnson@futureworks.de', 'Mike', 'Johnson'),
('EMP004', 'emma.watson@skynet.au', 'Emma', 'Watson');
