CREATE TABLE short_url(
    id BIGSERIAL PRIMARY KEY,
    long_url varchar NOT NULL,
    short_id varchar NOT NULL UNIQUE,
    counter INTEGER DEFAULT 0,
    date_created TIMESTAMP DEFAULT now(),
    expiration_date TIMESTAMP NOT NULL,
    created_by BIGINT REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS short_url_short_id_index ON short_url(short_id);