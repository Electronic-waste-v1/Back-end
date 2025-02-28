CREATE TABLE annonces (
                          id BIGSERIAL PRIMARY KEY,
                          titre VARCHAR(255) NOT NULL,
                          description TEXT NOT NULL,
                          date_publication DATE NOT NULL,
                          etat VARCHAR(50) NOT NULL,
                          prix DECIMAL(10,2),
                          utilisateur_id BIGINT NOT NULL REFERENCES users(id),
                          ewaste_id BIGINT NOT NULL REFERENCES ewastes(id),
                          disponibilite BOOLEAN DEFAULT TRUE,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);