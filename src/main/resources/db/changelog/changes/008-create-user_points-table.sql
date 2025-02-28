CREATE TABLE user_points (
                             id BIGSERIAL PRIMARY KEY,
                             utilisateur_id BIGINT NOT NULL REFERENCES users(id),
                             points_total INTEGER NOT NULL DEFAULT 0,
                             points_utilises INTEGER NOT NULL DEFAULT 0,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);