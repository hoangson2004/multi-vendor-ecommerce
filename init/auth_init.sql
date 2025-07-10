CREATE SCHEMA IF NOT EXISTS auth_schema;

CREATE TABLE IF NOT EXISTS auth_schema.users (
    id UUID PRIMARY KEY,
    user_ref VARCHAR(50) UNIQUE NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password TEXT NOT NULL,
    role VARCHAR(20) NOT NULL,
    provider VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS auth_schema.refresh_tokens (
    id UUID PRIMARY KEY,
    user_id UUID,
    token TEXT,
    expires_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES auth_schema.users(id)
);
