CREATE TABLE community_post (
                                id SERIAL PRIMARY KEY,
                                title VARCHAR(255) NOT NULL,
                                content VARCHAR(2000) NOT NULL,
                                user_id INTEGER NOT NULL REFERENCES users(id),
                                image_url VARCHAR(255),
                                likes_count INTEGER NOT NULL DEFAULT 0,
                                comments_count INTEGER NOT NULL DEFAULT 0,
                                created_at TIMESTAMP NOT NULL,
                                updated_at TIMESTAMP
);

CREATE TABLE community_post_tags (
                                     post_id INTEGER NOT NULL REFERENCES community_post(id),
                                     tag VARCHAR(255),
                                     PRIMARY KEY (post_id, tag)
);

CREATE TABLE community_post_likes (
                                      post_id INTEGER NOT NULL REFERENCES community_post(id),
                                      user_id INTEGER NOT NULL REFERENCES users(id),
                                      PRIMARY KEY (post_id, user_id)
);

CREATE TABLE comment (
                         id SERIAL PRIMARY KEY,
                         content VARCHAR(1000) NOT NULL,
                         user_id INTEGER NOT NULL REFERENCES users(id),
                         post_id INTEGER NOT NULL REFERENCES community_post(id),
                         likes_count INTEGER DEFAULT 0,
                         created_at TIMESTAMP NOT NULL,
                         updated_at TIMESTAMP
);

CREATE TABLE comment_likes (
                               comment_id INTEGER NOT NULL REFERENCES comment(id),
                               user_id INTEGER NOT NULL REFERENCES users(id),
                               PRIMARY KEY (comment_id, user_id)
);

CREATE TABLE community_event (
                                 id SERIAL PRIMARY KEY,
                                 title VARCHAR(255) NOT NULL,
                                 description VARCHAR(2000) NOT NULL,
                                 start_date TIMESTAMP NOT NULL,
                                 end_date TIMESTAMP NOT NULL,
                                 location VARCHAR(255) NOT NULL,
                                 image_url VARCHAR(255),
                                 organizer_id INTEGER NOT NULL REFERENCES users(id),
                                 attendees_count INTEGER DEFAULT 0,
                                 created_at TIMESTAMP NOT NULL,
                                 updated_at TIMESTAMP
);

CREATE TABLE event_attendees (
                                 event_id INTEGER NOT NULL REFERENCES community_event(id),
                                 user_id INTEGER NOT NULL REFERENCES users(id),
                                 PRIMARY KEY (event_id, user_id)
);

CREATE TABLE community_challenge (
                                     id SERIAL PRIMARY KEY,
                                     title VARCHAR(255) NOT NULL,
                                     description VARCHAR(2000) NOT NULL,
                                     start_date TIMESTAMP NOT NULL,
                                     end_date TIMESTAMP NOT NULL,
                                     image_url VARCHAR(255),
                                     reward VARCHAR(255) NOT NULL,
                                     target_goal INTEGER,
                                     current_progress INTEGER NOT NULL DEFAULT 0,
                                     participants_count INTEGER NOT NULL DEFAULT 0,
                                     creator_id INTEGER NOT NULL REFERENCES users(id),
                                     created_at TIMESTAMP NOT NULL,
                                     updated_at TIMESTAMP
);

CREATE TABLE challenge_participants (
                                        challenge_id INTEGER NOT NULL REFERENCES community_challenge(id),
                                        user_id INTEGER NOT NULL REFERENCES users(id),
                                        PRIMARY KEY (challenge_id, user_id)
);