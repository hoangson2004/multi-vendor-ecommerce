CREATE SCHEMA IF NOT EXISTS shipping_schema;

CREATE TABLE IF NOT EXISTS shipping_schema.shipments (
    id UUID PRIMARY KEY,
    order_id UUID,
    carrier VARCHAR(50),
    tracking_number VARCHAR(50),
    status VARCHAR(20),
    shipped_at TIMESTAMP
);
