CREATE TABLE recompenses (
                             id BIGSERIAL PRIMARY KEY,
                             description VARCHAR(255) NOT NULL,
                             points_requis INTEGER NOT NULL,
                             utilisateur_id BIGINT REFERENCES users(id),
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);