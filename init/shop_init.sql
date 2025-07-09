CREATE SCHEMA IF NOT EXISTS shop_schema;

CREATE TABLE IF NOT EXISTS shop_schema.shops (
    id UUID PRIMARY KEY,
    seller_id UUID NOT NULL,
    name TEXT NOT NULL,
    description TEXT,
    logo_url TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
