CREATE TYPE etatwaste_enum AS ENUM ('Recycler', 'Reparable', 'Donne');

CREATE TABLE ewaste (
                        id SERIAL PRIMARY KEY,
                        nom VARCHAR(255),
                        description VARCHAR(255),
                        categorie VARCHAR(255),
                        etat etatwaste_enum,
                        user_id INTEGER REFERENCES users(id)
);