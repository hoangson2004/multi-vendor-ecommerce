CREATE SCHEMA IF NOT EXISTS review_schema;

CREATE TABLE IF NOT EXISTS review_schema.product_reviews (
    id UUID PRIMARY KEY,
    product_id UUID,
    user_id UUID,
    rating INT,
    comment TEXT,
    created_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS review_schema.shop_reviews (
    id UUID PRIMARY KEY,
    shop_id UUID,
    user_id UUID,
    rating INT,
    comment TEXT,
    created_at TIMESTAMP
);
