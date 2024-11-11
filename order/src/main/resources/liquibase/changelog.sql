-- liquibase formatted sql

-- changeset initial:1
-- tag versao_1.0

CREATE TABLE products (
d BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
model VARCHAR(255) NOT NULL,
price DOUBLE PRECISION NOT NULL
);

INSERT INTO products (name, model, price) VALUES
('Laptop', 'Model A', 999.99),
('Smartphone', 'Model X', 499.49),
('Tablet', 'Model T', 299.99),
('Smartwatch', 'Model S', 199.99),
('Headphones', 'Model H', 89.99);