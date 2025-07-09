CREATE SCHEMA IF NOT EXISTS product_schema;

CREATE TABLE IF NOT EXISTS product_schema.categories (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS product_schema.products (
    id UUID PRIMARY KEY,
    shop_id UUID,
    category_id UUID,
    name TEXT,
    description TEXT,
    price NUMERIC(10,2),
    quantity INT,
    image_urls JSONB,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
