
DROP DATABASE IF EXISTS money_loan_system;

CREATE DATABASE money_loan_system;

USE money_loan_system;


CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('CUSTOMER', 'ADMIN') NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE'
);

CREATE TABLE customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,

    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,

    email VARCHAR(100),
    phone VARCHAR(20),
    address VARCHAR(255),

    salary DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    credit_limit DECIMAL(10,2) DEFAULT 10000.00,

    FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


CREATE TABLE loans (
    loan_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,

    loan_amount DECIMAL(10,2) NOT NULL,
    interest_rate DECIMAL(5,2) NOT NULL,
    term_months INT NOT NULL,

    total_amount DECIMAL(10,2) NOT NULL,
    remaining_balance DECIMAL(10,2) NOT NULL,

    application_date DATE NOT NULL,
    due_date DATE,

    status ENUM(
        'PENDING',
        'APPROVED',
        'REJECTED',
        'PAID',
        'OVERDUE'
    ) DEFAULT 'PENDING',

    FOREIGN KEY (customer_id)
        REFERENCES customers(customer_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


CREATE TABLE payments (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    loan_id INT NOT NULL,

    payment_amount DECIMAL(10,2) NOT NULL,
    payment_date DATE NOT NULL,
    payment_method VARCHAR(30),

    FOREIGN KEY (loan_id)
        REFERENCES loans(loan_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE credit_history (
    credit_history_id INT PRIMARY KEY AUTO_INCREMENT,

    customer_id INT NOT NULL,
    loan_id INT,

    payment_status VARCHAR(30),

    missed_payments INT DEFAULT 0,
    late_payments INT DEFAULT 0,

    FOREIGN KEY (customer_id)
        REFERENCES customers(customer_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    FOREIGN KEY (loan_id)
        REFERENCES loans(loan_id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

CREATE TABLE liens (
    lien_id INT PRIMARY KEY AUTO_INCREMENT,

    customer_id INT NOT NULL,
    loan_id INT NOT NULL,

    lien_reason VARCHAR(255) NOT NULL,
    lien_date DATE NOT NULL,

    status ENUM(
        'ACTIVE',
        'RELEASED'
    ) DEFAULT 'ACTIVE',

    FOREIGN KEY (customer_id)
        REFERENCES customers(customer_id)
        ON DELETE CASCADE,

    FOREIGN KEY (loan_id)
        REFERENCES loans(loan_id)
        ON DELETE CASCADE
);

-- ADMIN
INSERT INTO users
(username, password, role, status)
VALUES
('admin', 'admin123', 'ADMIN', 'ACTIVE');









-- CHECK

SHOW TABLES;


SELECT * FROM users;
SELECT * FROM customers;
SELECT * FROM loans;
SELECT * FROM payments;
SELECT * FROM credit_history;
SELECT * FROM liens;



