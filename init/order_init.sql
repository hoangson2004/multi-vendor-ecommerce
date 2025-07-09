CREATE SCHEMA IF NOT EXISTS order_schema;

CREATE TABLE IF NOT EXISTS order_schema.orders (
    id UUID PRIMARY KEY,
    user_id UUID,
    total_amount NUMERIC,
    status VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS order_schema.order_items (
    id UUID PRIMARY KEY,
    order_id UUID,
    product_id UUID,
    shop_id UUID,
    quantity INT,
    price NUMERIC,
    FOREIGN KEY (order_id) REFERENCES order_schema.orders(id)
);
