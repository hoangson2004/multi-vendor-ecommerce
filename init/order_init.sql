CREATE SCHEMA order_schema;

CREATE TABLE IF NOT EXISTS order_schema.carts (
    id UUID PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS order_schema.cart_items (
    id UUID PRIMARY KEY,
    cart_uuid UUID NOT NULL REFERENCES order_schema.carts(id) ON DELETE CASCADE,
    variant_id VARCHAR(20) NOT NULL,
    vendor_id VARCHAR(20) NOT NULL,
    vendor_product_id VARCHAR(20),
    vendor_product_name VARCHAR(255),
    vendor_product_url VARCHAR(500),
    quantity INT NOT NULL,
    price DECIMAL(15,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS order_schema.orders (
    id UUID PRIMARY KEY,
    order_code VARCHAR(50) UNIQUE NOT NULL,
    user_id VARCHAR(20) NOT NULL,
    vendor_id VARCHAR(20) NOT NULL,
    total_amount DECIMAL(15,2) NOT NULL,
    status INT NOT NULL DEFAULT 1,
    payment_status INT NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS order_schema.order_items (
    id UUID PRIMARY KEY,
    order_uuid UUID NOT NULL REFERENCES order_schema.orders(id) ON DELETE CASCADE,
    product_id VARCHAR(20) NOT NULL,
    variant_id VARCHAR(20) NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(15,2) NOT NULL,
    subtotal DECIMAL(15,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS order_schema.order_history (
    id UUID PRIMARY KEY,
    order_uuid UUID NOT NULL REFERENCES order_schema.orders(id) ON DELETE CASCADE,
    status INT NOT NULL,
    note TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

