CREATE TABLE notification (
                              id SERIAL PRIMARY KEY,
                              user_id INTEGER NOT NULL REFERENCES users(id),
                              message VARCHAR(255) NOT NULL,
                              date_envoi DATE NOT NULL,
                              est_lu BOOLEAN DEFAULT false
);