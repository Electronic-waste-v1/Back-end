CREATE TABLE feedbacks (
                           id BIGSERIAL PRIMARY KEY,
                           utilisateur1_id BIGINT NOT NULL REFERENCES users(id),
                           utilisateur2_id BIGINT NOT NULL REFERENCES users(id),
                           commentaire VARCHAR(500),
                           note INTEGER CHECK (note BETWEEN 1 AND 5),
                           date_feedback DATE NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);