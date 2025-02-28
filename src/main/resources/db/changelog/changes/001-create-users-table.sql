CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       nom VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       telephone VARCHAR(20),
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(20),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);