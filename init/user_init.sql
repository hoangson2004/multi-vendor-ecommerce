CREATE SCHEMA IF NOT EXISTS user_schema;
CREATE EXTENSION IF NOT EXISTS unaccent;

CREATE TABLE IF NOT EXISTS user_schema.user_profiles (
    id UUID PRIMARY KEY,
    user_id VARCHAR(20) UNIQUE NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    full_name VARCHAR(100),
    avatar_url TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);
CREATE INDEX idx_user_profiles_user_id ON user_schema.user_profiles(user_id);

CREATE TABLE IF NOT EXISTS user_schema.user_addresses (
    id UUID PRIMARY KEY,
    user_id VARCHAR(20)NOT NULL,
    recipient_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address_line TEXT NOT NULL,
    ward_code VARCHAR(10),
    district_code VARCHAR(10),
    province_code VARCHAR(10),
    is_default BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    FOREIGN KEY (user_id) REFERENCES user_schema.user_profiles(user_id) ON DELETE CASCADE
);
CREATE INDEX idx_user_addresses_user_id ON user_schema.user_addresses(user_id);


CREATE TABLE IF NOT EXISTS user_schema.user_activity_logs (
    id UUID PRIMARY KEY,
    user_id VARCHAR(20) NOT NULL,
    action VARCHAR(100) NOT NULL,
    description TEXT,
    ip_address VARCHAR(50),
    user_agent TEXT,
    created_at TIMESTAMP DEFAULT now(),
    FOREIGN KEY (user_id) REFERENCES user_schema.user_profiles(user_id) ON DELETE CASCADE
);
CREATE INDEX idx_user_activity_logs_user_id ON user_schema.user_activity_logs(user_id);

CREATE TABLE IF NOT EXISTS user_schema.user_roles (
    id UUID PRIMARY KEY,
    user_id VARCHAR(20) NOT NULL,
    role VARCHAR(10) NOT NULL,
    assigned_at TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (user_id) REFERENCES user_schema.user_profiles(user_id) ON DELETE CASCADE
);
CREATE INDEX idx_user_roles_user_id ON user_schema.user_roles(user_id);
