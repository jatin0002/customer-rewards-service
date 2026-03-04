DROP TABLE IF EXISTS reward_transaction;
DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
    customer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL
);

CREATE TABLE transactions (
    transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(10,2) NOT NULL,
    transaction_date DATE NOT NULL,
    customer_id BIGINT NOT NULL,
    CONSTRAINT fk_customer
        FOREIGN KEY (customer_id)
        REFERENCES customer(customer_id)
);

INSERT INTO customer (customer_name) VALUES
('Jatin Pratap Singh'),
('Rahul Sharma'),
('Anita Verma'),
('Vikram M');



INSERT INTO transactions (amount, transaction_date, customer_id) VALUES
(120.00, DATE '2026-01-10', 1),
(75.00,  DATE '2026-02-15', 1),
(45.00,  DATE '2026-03-01', 1),
(180.00, DATE '2026-03-12', 1);

INSERT INTO transactions (amount, transaction_date, customer_id) VALUES
(200.00, DATE '2026-01-20', 2),
(90.00,  DATE '2026-02-05', 2),
(60.00,  DATE '2026-02-28', 2);

INSERT INTO transactions (amount, transaction_date, customer_id) VALUES
(55.00,  DATE '2026-01-05', 3),
(130.00, DATE '2026-02-11', 3),
(250.00, DATE '2026-03-18', 3);

INSERT INTO transactions (amount, transaction_date, customer_id) VALUES
(49.00,  DATE '2026-01-08', 4),
(101.00, DATE '2026-02-14', 4),
(300.00, DATE '2026-03-25', 4);