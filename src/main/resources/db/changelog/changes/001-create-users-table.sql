CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(255),
                       email VARCHAR(255),
                       password VARCHAR(255),
                       role VARCHAR(50)
);