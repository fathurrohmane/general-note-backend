CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS roles(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

CREATE INDEX IF NOT EXISTS roles_name_index ON roles(name);
ALTER TABLE roles
ALTER COLUMN id TYPE BIGINT;

CREATE TABLE IF NOT EXISTS users(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE CHECK(char_length(username) between 5 AND 50),
    password VARCHAR NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS users_username_index ON users(username);
ALTER TABLE users
ALTER COLUMN id TYPE BIGINT;

CREATE TABLE IF NOT EXISTS user_role(
    users_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    roles_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE RESTRICT,
    PRIMARY KEY (users_id, roles_id)
);
ALTER TABLE users
ALTER COLUMN users_id TYPE BIGINT,
ALTER COLUMN roles_id TYPE BIGINT;

CREATE TABLE IF NOT EXISTS notes(
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200),
    content VARCHAR NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP
);
ALTER TABLE notes
ALTER COLUMN id TYPE BIGINT;

CREATE TABLE IF NOT EXISTS user_token(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    token_id VARCHAR NOT NULL,
    user_agent VARCHAR(200),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS user_token_token_id_index ON user_token(token_id);
ALTER TABLE user_token
ALTER COLUMN user_id TYPE BIGINT;
