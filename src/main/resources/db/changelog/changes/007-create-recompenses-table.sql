CREATE TABLE recompense (
                            id SERIAL PRIMARY KEY,
                            description VARCHAR(255),
                            points_requis INTEGER,
                            user_id INTEGER REFERENCES users(id)
);