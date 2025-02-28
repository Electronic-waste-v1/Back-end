CREATE TABLE notifications (
                               id BIGSERIAL PRIMARY KEY,
                               utilisateur_id BIGINT NOT NULL REFERENCES users(id),
                               message VARCHAR(500) NOT NULL,
                               date_envoi DATE NOT NULL,
                               est_lu BOOLEAN DEFAULT FALSE,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);