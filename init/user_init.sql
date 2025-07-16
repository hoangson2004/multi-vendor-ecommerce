CREATE SCHEMA IF NOT EXISTS user_schema;

CREATE TABLE IF NOT EXISTS user_schema.user_profiles (
    id UUID PRIMARY KEY,
    user_id VARCHAR(20) UNIQUE NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    full_name VARCHAR(100),
    avatar_url TEXT,
    role VARCHAR(20) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE user_addresses (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    recipient_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address_line TEXT NOT NULL,
    ward_code VARCHAR(10),
    district_code VARCHAR(10),
    province_code VARCHAR(10),
    is_default BOOLEAN DEFAULT FALSE,
    is_deleted BOOLEAN,
    create_at TIMESTAMP DEFAULT now(),
    update_at TIMESTAMP DEFAULT now(),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE user_activity_logs (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    action VARCHAR(100) NOT NULL,
    description TEXT,
    ip_address VARCHAR(45),
    user_agent TEXT,
    create_at TIMESTAMP DEFAULT now(),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_user_logs_user_id ON user_activity_logs(user_id);

CREATE TABLE user_roles (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    role VARCHAR(30) NOT NULL,
    assigned_at TIMESTAMP DEFAULT NOW()
);
