CREATE SCHEMA IF NOT EXISTS auth_schema;

CREATE TABLE IF NOT EXISTS auth_schema.users (
    id UUID PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS auth_schema.refresh_tokens (
    id UUID PRIMARY KEY,
    user_id UUID,
    token TEXT,
    expires_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES auth_schema.users(id)
);
