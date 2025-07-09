CREATE SCHEMA IF NOT EXISTS payment_schema;

CREATE TABLE IF NOT EXISTS payment_schema.payments (
    id UUID PRIMARY KEY,
    order_id UUID,
    payment_method VARCHAR(20),
    amount NUMERIC,
    status VARCHAR(20),
    transaction_time TIMESTAMP
);
