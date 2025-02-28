CREATE TABLE recycling_centers (
                                   id BIGSERIAL PRIMARY KEY,
                                   nom VARCHAR(255) NOT NULL,
                                   adresse VARCHAR(255) NOT NULL,
                                   ville VARCHAR(100) NOT NULL,
                                   contact VARCHAR(100),
                                   types_acceptes VARCHAR(500),
                                   latitude DECIMAL(10,7),
                                   longitude DECIMAL(10,7),
                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);