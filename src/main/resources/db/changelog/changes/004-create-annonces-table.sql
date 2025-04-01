CREATE TYPE etat_enum AS ENUM ('Disponible', 'Reserve', 'Donne');

CREATE TABLE annonce (
                         id SERIAL PRIMARY KEY,
                         title VARCHAR(255),
                         description VARCHAR(255),
                         prix DOUBLE PRECISION,
                         etat etat_enum,
                         user_id INTEGER REFERENCES users(id),
                         waste_id INTEGER REFERENCES ewaste(id),
                         disponibilite BOOLEAN DEFAULT true
);

CREATE TABLE annonce_image (
                               id SERIAL PRIMARY KEY,
                               name VARCHAR(255),
                               type VARCHAR(255),
                               path VARCHAR(255),
                               annonce_id INTEGER REFERENCES annonce(id)
);