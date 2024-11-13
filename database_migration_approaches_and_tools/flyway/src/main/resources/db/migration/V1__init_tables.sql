
CREATE TABLE company (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    country_code VARCHAR(255)
);

CREATE TABLE employee (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255),
    last_name VARCHAR(255)
);