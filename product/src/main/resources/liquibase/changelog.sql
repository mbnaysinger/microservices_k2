-- liquibase formatted sql

-- changeset initial:1
-- tag versao_1.0
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT DEFAULT 0,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    isbn VARCHAR(13) NOT NULL,
    published_date DATE,
    author_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT DEFAULT 0,
    deleted BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_books_authors FOREIGN KEY (author_id) REFERENCES products(id)
);

-- changeset test:1 context:local
INSERT INTO products (name, created_by, updated_by, version, deleted) VALUES
('Author One', 'system', 'system', 0, FALSE),
('Author Two', 'system', 'system', 0, FALSE),
('Author Three', 'system', 'system', 0, FALSE),
('Author Four', 'system', 'system', 0, FALSE),
('Author Five', 'system', 'system', 0, FALSE),
('Author Six', 'system', 'system', 0, FALSE),
('Author Seven', 'system', 'system', 0, FALSE),
('Author Eight', 'system', 'system', 0, FALSE),
('Author Nine', 'system', 'system', 0, FALSE),
('Author Ten', 'system', 'system', 0, FALSE),
('Author Eleven', 'system', 'system', 0, FALSE),
('Author Twelve', 'system', 'system', 0, FALSE),
('Author Thirteen', 'system', 'system', 0, FALSE),
('Author Fourteen', 'system', 'system', 0, FALSE),
('Author Fifteen', 'system', 'system', 0, FALSE);

INSERT INTO books (title, isbn, published_date, author_id, created_by, updated_by, version, deleted) VALUES 
('Book One', '1234567890123', '2024-01-01', 1, 'system', 'system', 0, FALSE),
('Book Two', '1234567890124', '2024-01-02', 2, 'system', 'system', 0, FALSE),
('Book Three', '1234567890125', '2024-01-03', 3, 'system', 'system', 0, FALSE),
('Book Four', '1234567890126', '2024-01-04', 4, 'system', 'system', 0, FALSE),
('Book Five', '1234567890127', '2024-01-05', 5, 'system', 'system', 0, FALSE),
('Book Six', '1234567890128', '2024-01-06', 6, 'system', 'system', 0, FALSE),
('Book Seven', '1234567890129', '2024-01-07', 7, 'system', 'system', 0, FALSE),
('Book Eight', '1234567890130', '2024-01-08', 8, 'system', 'system', 0, FALSE),
('Book Nine', '1234567890131', '2024-01-09', 9, 'system', 'system', 0, FALSE),
('Book Ten', '1234567890132', '2024-01-10', 10, 'system', 'system', 0, FALSE),
('Book Eleven', '1234567890133', '2024-01-11', 11, 'system', 'system', 0, FALSE),
('Book Twelve', '1234567890134', '2024-01-12', 12, 'system', 'system', 0, FALSE),
('Book Thirteen', '1234567890135', '2024-01-13', 13, 'system', 'system', 0, FALSE),
('Book Fourteen', '1234567890136', '2024-01-14', 14, 'system', 'system', 0, FALSE),
('Book Fifteen', '1234567890137', '2024-01-15', 15, 'system', 'system', 0, FALSE),
('Book Sixteen', '1234567890138', '2024-01-16', 1, 'system', 'system', 0, FALSE),
('Book Seventeen', '1234567890139', '2024-01-17', 2, 'system', 'system', 0, FALSE),
('Book Eighteen', '1234567890140', '2024-01-18', 3, 'system', 'system', 0, FALSE),
('Book Nineteen', '1234567890141', '2024-01-19', 4, 'system', 'system', 0, FALSE),
('Book Twenty', '1234567890142', '2024-01-20', 5, 'system', 'system', 0, FALSE),
('Book Twenty-One', '1234567890143', '2024-01-21', 6, 'system', 'system', 0, FALSE),
('Book Twenty-Two', '1234567890144', '2024-01-22', 7, 'system', 'system', 0, FALSE),
('Book Twenty-Three', '1234567890145', '2024-01-23', 8, 'system', 'system', 0, FALSE),
('Book Twenty-Four', '1234567890146', '2024-01-24', 9, 'system', 'system', 0, FALSE),
('Book Twenty-Five', '1234567890147', '2024-01-25', 10, 'system', 'system', 0, FALSE),
('Book Twenty-Six', '1234567890148', '2024-01-26', 11, 'system', 'system', 0, FALSE),
('Book Twenty-Seven', '1234567890149', '2024-01-27', 12, 'system', 'system', 0, FALSE),
('Book Twenty-Eight', '1234567890150', '2024-01-28', 13, 'system', 'system', 0, FALSE),
('Book Twenty-Nine', '1234567890151', '2024-01-29', 14, 'system', 'system', 0, FALSE),
('Book Thirty', '1234567890152', '2024-01-30', 15, 'system', 'system', 0, FALSE);