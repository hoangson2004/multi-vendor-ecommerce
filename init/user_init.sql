CREATE SCHEMA IF NOT EXISTS user_schema;

CREATE TABLE IF NOT EXISTS user_schema.user_profiles (
    user_id UUID PRIMARY KEY,
    full_name TEXT,
    phone VARCHAR(15),
    avatar_url TEXT,
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
