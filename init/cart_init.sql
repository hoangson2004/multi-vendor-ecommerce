CREATE SCHEMA IF NOT EXISTS cart_schema;

CREATE TABLE IF NOT EXISTS cart_schema.carts (
    user_id UUID PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS cart_schema.cart_items (
    id UUID PRIMARY KEY,
    cart_id UUID,
    product_id UUID,
    quantity INT,
    price_at_time NUMERIC(10,2),
    FOREIGN KEY (cart_id) REFERENCES cart_schema.carts(user_id)
);
