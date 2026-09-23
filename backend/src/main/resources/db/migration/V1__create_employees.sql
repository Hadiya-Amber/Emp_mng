-- Flyway migration: create employees table
-- create a sequence for id generation and use it as default for id column
CREATE SEQUENCE employees_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE employees (
    id BIGINT DEFAULT NEXT VALUE FOR employees_id_seq PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    department VARCHAR(255),
    job_title VARCHAR(255),
    hire_date DATE
);

CREATE UNIQUE INDEX uk_employees_email ON employees (email);
