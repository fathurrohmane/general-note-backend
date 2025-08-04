CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS roles(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

CREATE INDEX IF NOT EXISTS roles_name_index ON roles(name);

CREATE TABLE IF NOT EXISTS users(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE CHECK(char_length(username) between 5 AND 50),
    password VARCHAR NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS users_username_index ON users(username);

CREATE TABLE IF NOT EXISTS user_role(
    users_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    roles_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE RESTRICT,
    PRIMARY KEY (users_id, roles_id)
);

CREATE TABLE IF NOT EXISTS notes(
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200),
    content VARCHAR NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE
);
ALTER TABLE notes
ADD COLUMN IF NOT EXISTS user_id BIGINT REFERENCES users(id) ON DELETE CASCADE;

CREATE TABLE IF NOT EXISTS user_token(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    token_id VARCHAR NOT NULL,
    user_agent VARCHAR(200),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS user_token_token_id_index ON user_token(token_id);
