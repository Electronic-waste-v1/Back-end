CREATE TABLE actions_historique (
                                    id SERIAL PRIMARY KEY,
                                    user_id INTEGER NOT NULL REFERENCES users(id),
                                    action_type VARCHAR(255),
                                    description VARCHAR(255),
                                    points_gagnes INTEGER,
                                    date_action TIMESTAMP,
                                    ewaste_id INTEGER REFERENCES ewaste(id)
);