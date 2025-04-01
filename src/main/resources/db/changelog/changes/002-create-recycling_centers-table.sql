CREATE TYPE status_enum AS ENUM ('Open', 'Closed');

CREATE TABLE recycling_center (
                                  id SERIAL PRIMARY KEY,
                                  contact VARCHAR(255) NOT NULL,
                                  nom VARCHAR(255) NOT NULL,
                                  adresse VARCHAR(255) NOT NULL,
                                  status status_enum,
                                  hours VARCHAR(255),
                                  description VARCHAR(1000),
                                  phone VARCHAR(255),
                                  email VARCHAR(255),
                                  image VARCHAR(255),
                                  distance VARCHAR(255),
                                  rating DOUBLE PRECISION,
                                  last_visited TIMESTAMP,
                                  lat DOUBLE PRECISION,
                                  lng DOUBLE PRECISION
);

CREATE TABLE collection_point_accepted_types (
                                                 collection_point_id INTEGER NOT NULL REFERENCES recycling_center(id),
                                                 type VARCHAR(255),
                                                 PRIMARY KEY (collection_point_id, type)
);