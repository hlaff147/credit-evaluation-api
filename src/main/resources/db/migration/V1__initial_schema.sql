-- Create clients table
CREATE TABLE clients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    income DOUBLE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
);

-- Create client_credit_types table for the @ElementCollection
CREATE TABLE client_credit_types (
    client_id BIGINT NOT NULL,
    credit_type VARCHAR(50) NOT NULL,
    PRIMARY KEY (client_id, credit_type),
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE
);