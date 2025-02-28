CREATE TABLE ewastes (
                         id BIGSERIAL PRIMARY KEY,
                         nom VARCHAR(255) NOT NULL,
                         categorie VARCHAR(100) NOT NULL,
                         description TEXT,
                         date_ajout DATE NOT NULL,
                         etat VARCHAR(50) NOT NULL CHECK (etat IN ('À Recycler', 'Réparable', 'Donné')),
                         utilisateur_id BIGINT NOT NULL REFERENCES users(id),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
