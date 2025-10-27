CREATE SCHEMA order_schema;

CREATE TABLE IF NOT EXISTS order_schema.carts (
    id UUID PRIMARY KEY,
    user_id VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS order_schema.cart_items (
    id UUID PRIMARY KEY,
    cart_uuid UUID NOT NULL REFERENCES order_schema.carts(id) ON DELETE CASCADE,
    variant_id VARCHAR(20) NOT NULL,
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

INSERT INTO order_schema.carts (id, user_id, created_at)
VALUES
    (gen_random_uuid(), gen_random_uuid(), NOW()),
    (gen_random_uuid(), gen_random_uuid(), NOW());

INSERT INTO order_schema.cart_items (id, cart_uuid, variant_id, product_name, product_url, quantity, price, created_at)
VALUES
    (gen_random_uuid(), (SELECT id FROM order_schema.carts LIMIT 1), 'P1001', 'iPhone 15 Pro', 'wasd.com', 1, 29990000, NOW()),
    (gen_random_uuid(), (SELECT id FROM order_schema.carts LIMIT 1), 'P1002', 'MacBook Air M3', 'wqeqweqwe.com', 1, 28990000, NOW()),
    (gen_random_uuid(), (SELECT id FROM order_schema.carts OFFSET 1 LIMIT 1), 'P2001', 'AirPods Pro 2', 'asdasdasd.cccc', 2, 5290000, NOW());

INSERT INTO order_schema.orders (id, order_code, user_id, vendor_id, total_amount, status, payment_status, created_at, updated_at)
VALUES
    (gen_random_uuid(), 'ORD-20250925-001', 'U1001', 'V2001', 58980000, 1, 1, NOW(), NOW()),
    (gen_random_uuid(), 'ORD-20250925-002', 'U1002', 'V2002', 10580000, 2, 1, NOW(), NOW());

INSERT INTO order_schema.order_items (id, order_uuid, product_id, variant_id, product_name, quantity, price, subtotal)
VALUES
    (gen_random_uuid(), (SELECT id FROM order_schema.orders WHERE order_code = 'ORD-20250925-001'), 'P1001', 'VAR-01', 'iPhone 15 Pro', 1, 29990000, 29990000),
    (gen_random_uuid(), (SELECT id FROM order_schema.orders WHERE order_code = 'ORD-20250925-001'), 'P1002', 'VAR-02', 'MacBook Air M3', 1, 28990000, 28990000),
    (gen_random_uuid(), (SELECT id FROM order_schema.orders WHERE order_code = 'ORD-20250925-002'), 'P2001', 'VAR-03', 'AirPods Pro 2', 2, 5290000, 10580000);

INSERT INTO order_schema.order_history (id, order_uuid, status, note, created_at)
VALUES
    (gen_random_uuid(), (SELECT id FROM order_schema.orders WHERE order_code = 'ORD-20250925-001'), 1, 'Order created', NOW()),
    (gen_random_uuid(), (SELECT id FROM order_schema.orders WHERE order_code = 'ORD-20250925-001'), 2, 'Payment confirmed', NOW()),
    (gen_random_uuid(), (SELECT id FROM order_schema.orders WHERE order_code = 'ORD-20250925-002'), 1, 'Order created', NOW());