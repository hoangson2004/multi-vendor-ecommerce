
-- ====================================================================
-- BIG SEED SCRIPT FOR MULTI-VENDOR ECOMMERCE
-- Schemas: auth_schema, user_schema, product_schema, order_schema
-- NOTE:
--   - Assumes schemas & tables are already created by your *_init.sql.
--   - Uses pgcrypto.gen_random_uuid(), so requires pgcrypto extension.
--   - Inserts data at scale ~1M users + related objects.
--   - You can reduce generate_series() ranges if needed.
-- ====================================================================

CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- ====================================================================
-- 1. AUTH & USER DATA
-- ====================================================================

-- --------------------------------------------------------------------
-- 1.1 auth_schema.users  (~1,000,000 rows)
-- --------------------------------------------------------------------
INSERT INTO auth_schema.users (
    id,
    user_id,
    username,
    email,
    password,
    role,
    provider,
    is_locked,
    locked_reason,
    locked_until,
    is_banned
)
SELECT
    gen_random_uuid() AS id,
    'USR' || LPAD(gs::text, 9, '0') AS user_id,         -- USR000000001 .. USR100000000
    'user_' || gs AS username,
    'user' || gs || '@example.com' AS email,
    '$2a$10$dev_seed_dummy_password_hash' AS password,  -- dummy hash for dev
    CASE
        WHEN gs % 100000 = 0 THEN 'ADMIN'
        WHEN gs % 5 = 0 THEN 'VENDOR'
        ELSE 'USER'
    END AS role,
    'LOCAL' AS provider,
    FALSE AS is_locked,
    NULL::TEXT AS locked_reason,
    NULL::TIMESTAMP AS locked_until,
    FALSE AS is_banned
FROM generate_series(1, 1000000) AS gs;


-- --------------------------------------------------------------------
-- 1.2 user_schema.user_profiles  (~1,000,000 rows)
-- --------------------------------------------------------------------
INSERT INTO user_schema.user_profiles (
    id,
    user_id,
    username,
    email,
    phone,
    full_name,
    avatar_url,
    is_active,
    created_at,
    updated_at
)
SELECT
    gen_random_uuid() AS id,
    'USR' || LPAD(gs::text, 9, '0') AS user_id,
    'user_' || gs AS username,
    'user' || gs || '@example.com' AS email,
    '09' || LPAD((FLOOR(random()*100000000))::text, 8, '0') AS phone,
    'User ' || gs AS full_name,
    'https://example.com/avatar/' || gs || '.png' AS avatar_url,
    TRUE AS is_active,
    NOW() - (random() * INTERVAL '365 days') AS created_at,
    NOW() AS updated_at
FROM generate_series(1, 1000000) AS gs;


-- --------------------------------------------------------------------
-- 1.3 user_schema.user_addresses
--     - each user has 1 default address
--     - ~30% users have an extra address
-- --------------------------------------------------------------------
INSERT INTO user_schema.user_addresses (
    id,
    user_id,
    recipient_name,
    phone,
    address_line,
    ward_code,
    district_code,
    province_code,
    is_default,
    is_active,
    created_at,
    updated_at
)
SELECT
    gen_random_uuid() AS id,
    up.user_id,
    COALESCE(up.full_name, 'Recipient ' || up.user_id) AS recipient_name,
    COALESCE(
        up.phone,
        '09' || LPAD((FLOOR(random()*100000000))::text, 8, '0')
    ) AS phone,
    'Address ' || up.user_id || ' #' || addr_no AS address_line,
    LPAD((FLOOR(random()*10000))::text, 4, '0') AS ward_code,
    LPAD((FLOOR(random()*10000))::text, 4, '0') AS district_code,
    LPAD((FLOOR(random()*10000))::text, 4, '0') AS province_code,
    (addr_no = 1) AS is_default,
    TRUE AS is_active,
    NOW() - (random() * INTERVAL '365 days') AS created_at,
    NOW() AS updated_at
FROM user_schema.user_profiles up
CROSS JOIN generate_series(1, 2) AS g(addr_no)
WHERE
    addr_no = 1
    OR (addr_no = 2 AND random() < 0.3);


-- --------------------------------------------------------------------
-- 1.4 user_schema.user_roles
--     - everyone gets USER
--     - ~1% also ADMIN
--     - ~5% also VENDOR
-- --------------------------------------------------------------------
INSERT INTO user_schema.user_roles (
    id,
    user_id,
    role,
    assigned_at
)
SELECT
    gen_random_uuid(),
    up.user_id,
    'USER' AS role,
    NOW() - (random() * INTERVAL '365 days') AS assigned_at
FROM user_schema.user_profiles up;

INSERT INTO user_schema.user_roles (
    id,
    user_id,
    role,
    assigned_at
)
SELECT
    gen_random_uuid(),
    up.user_id,
    'ADMIN' AS role,
    NOW() - (random() * INTERVAL '365 days') AS assigned_at
FROM user_schema.user_profiles up
WHERE random() < 0.01;    -- ~1% admins

INSERT INTO user_schema.user_roles (
    id,
    user_id,
    role,
    assigned_at
)
SELECT
    gen_random_uuid(),
    up.user_id,
    'VENDOR' AS role,
    NOW() - (random() * INTERVAL '365 days') AS assigned_at
FROM user_schema.user_profiles up
WHERE random() < 0.05;    -- ~5% vendors


-- --------------------------------------------------------------------
-- 1.5 user_schema.user_activity_logs
--     - 2 logs per user
-- --------------------------------------------------------------------
INSERT INTO user_schema.user_activity_logs (
    id,
    user_id,
    action,
    description,
    ip_address,
    user_agent,
    created_at
)
SELECT
    gen_random_uuid(),
    up.user_id,
    CASE log_no
        WHEN 1 THEN 'LOGIN'
        ELSE 'VIEW_PRODUCT'
    END AS action,
    'Activity ' || log_no || ' of ' || up.user_id AS description,
    '192.168.' || (FLOOR(random()*255))::int || '.' || (FLOOR(random()*255))::int AS ip_address,
    'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36' AS user_agent,
    NOW() - (random() * INTERVAL '365 days') AS created_at
FROM user_schema.user_profiles up
CROSS JOIN generate_series(1, 2) AS g(log_no);


-- --------------------------------------------------------------------
-- 1.6 auth_schema.refresh_tokens
--     - tokens for ~10% users
-- --------------------------------------------------------------------
INSERT INTO auth_schema.refresh_tokens (
    id,
    user_id,
    token,
    client_ip,
    expires_at,
    created_at
)
SELECT
    gen_random_uuid() AS id,
    u.id AS user_id,
    encode(uuid_send(gen_random_uuid()), 'hex') ||
    encode(uuid_send(gen_random_uuid()), 'hex') AS token,
    '10.0.' || (FLOOR(random()*255))::int || '.' || (FLOOR(random()*255))::int AS client_ip,
    NOW() + INTERVAL '30 days' AS expires_at,
    NOW() - (random() * INTERVAL '10 days') AS created_at
FROM auth_schema.users u
WHERE random() < 0.1;


-- ====================================================================
-- 2. PRODUCT DATA
-- ====================================================================

-- --------------------------------------------------------------------
-- 2.1 product_schema.categories  (~100 rows)
-- --------------------------------------------------------------------
INSERT INTO product_schema.categories (
    id,
    category_id,
    parent_id,
    name,
    slug,
    description,
    created_at,
    updated_at,
    is_active
)
SELECT
    gen_random_uuid() AS id,
    'CAT' || LPAD(gs::text, 5, '0') AS category_id,
    NULL::UUID AS parent_id,
    'Category ' || gs AS name,
    'category-' || gs AS slug,
    'Sample description for category ' || gs AS description,
    NOW() - (random() * INTERVAL '365 days') AS created_at,
    NOW() AS updated_at,
    TRUE AS is_active
FROM generate_series(1, 100) AS gs;


-- --------------------------------------------------------------------
-- 2.2 product_schema.product_catalog  (~50,000 rows)
-- --------------------------------------------------------------------
INSERT INTO product_schema.product_catalog (
    id,
    catalog_id,
    name,
    description,
    brand,
    attributes_json,
    is_active,
    created_at,
    updated_at
)
SELECT
    gen_random_uuid() AS id,
    'PC' || LPAD(gs::text, 8, '0') AS catalog_id,
    'Product ' || gs AS name,
    'Description for product ' || gs AS description,
    (ARRAY['Apple','Samsung','Xiaomi','Sony','LG'])[FLOOR(random()*5)::int + 1] AS brand,
    jsonb_build_object(
        'model', 'M' || gs,
        'color', (ARRAY['Red','Blue','Green','Black','White'])[FLOOR(random()*5)::int + 1]
    ) AS attributes_json,
    TRUE AS is_active,
    NOW() - (random() * INTERVAL '365 days') AS created_at,
    NOW() AS updated_at
FROM generate_series(1, 50000) AS gs;


-- --------------------------------------------------------------------
-- 2.3 product_schema.product_categories
--     - each catalog assigned to 1 random category
-- --------------------------------------------------------------------
INSERT INTO product_schema.product_categories (
    catalog_uuid,
    category_uuid
)
SELECT
    pc.id AS catalog_uuid,
    c.id AS category_uuid
FROM product_schema.product_catalog pc
CROSS JOIN LATERAL (
    SELECT id
    FROM product_schema.categories
    ORDER BY random()
    LIMIT 1
) AS c;


-- --------------------------------------------------------------------
-- 2.4 product_schema.vendor_products
--     - 1–3 vendor products per catalog
-- --------------------------------------------------------------------
INSERT INTO product_schema.vendor_products (
    id,
    vendor_product_id,
    vendor_id,
    name,
    catalog_uuid,
    price,
    stock_quantity,
    status,
    created_at,
    updated_at
)
SELECT
    gen_random_uuid() AS id,
    'VP' || LPAD((ROW_NUMBER() OVER ())::text, 10, '0') AS vendor_product_id,
    'VENDOR-' || LPAD((FLOOR(random()*1000))::text, 4, '0') AS vendor_id,
    pc.name || ' (Vendor ' || vp_no || ')' AS name,
    pc.id AS catalog_uuid,
    ((FLOOR(random()*1000000)::numeric / 100) + 100000)::numeric(15,2) AS price,
    (FLOOR(random()*500))::int AS stock_quantity,
    1 AS status,
    NOW() - (random() * INTERVAL '365 days') AS created_at,
    NOW() AS updated_at
FROM product_schema.product_catalog pc
CROSS JOIN generate_series(1, 3) AS vp(vp_no);


-- --------------------------------------------------------------------
-- 2.5 product_schema.vendor_product_images
--     - one primary image per vendor_product
--     - optional extra images
-- --------------------------------------------------------------------
INSERT INTO product_schema.vendor_product_images (
    image_id,
    vendor_product_uuid,
    url,
    is_primary,
    created_at
)
SELECT
    gen_random_uuid() AS image_id,
    vp.id AS vendor_product_uuid,
    'https://img.example.com/vp/' || vp.vendor_product_id || '/main.jpg' AS url,
    TRUE AS is_primary,
    NOW() - (random() * INTERVAL '365 days') AS created_at
FROM product_schema.vendor_products vp;

INSERT INTO product_schema.vendor_product_images (
    image_id,
    vendor_product_uuid,
    url,
    is_primary,
    created_at
)
SELECT
    gen_random_uuid() AS image_id,
    vp.id AS vendor_product_uuid,
    'https://img.example.com/vp/' || vp.vendor_product_id || '/extra_' || img_idx || '.jpg' AS url,
    FALSE AS is_primary,
    NOW() - (random() * INTERVAL '365 days') AS created_at
FROM product_schema.vendor_products vp
CROSS JOIN generate_series(1, 3) AS g(img_idx)
WHERE random() < 0.5;


-- --------------------------------------------------------------------
-- 2.6 product_schema.product_variants
--     - 1–3 variants per vendor_product
-- --------------------------------------------------------------------
INSERT INTO product_schema.product_variants (
    id,
    variant_id,
    vendor_product_uuid,
    sku,
    attributes_json,
    price,
    stock_quantity,
    created_at,
    updated_at
)
SELECT
    gen_random_uuid() AS id,
    'VAR' || LPAD((ROW_NUMBER() OVER ())::text, 8, '0') AS variant_id,
    vp.id AS vendor_product_uuid,
    'SKU-' || vp.vendor_product_id || '-' || v_no AS sku,
    jsonb_build_object(
        'color', (ARRAY['Red','Blue','Green','Black','White'])[FLOOR(random()*5)::int + 1],
        'size',  (ARRAY['S','M','L','XL'])[FLOOR(random()*4)::int + 1]
    ) AS attributes_json,
    (vp.price + ((FLOOR(random()*200000)::numeric / 100) - 500.00))::numeric(15,2) AS price,
    GREATEST(0, (vp.stock_quantity - (FLOOR(random()*50))::int)) AS stock_quantity,
    NOW() - (random() * INTERVAL '365 days') AS created_at,
    NOW() AS updated_at
FROM product_schema.vendor_products vp
CROSS JOIN generate_series(1, 3) AS v(v_no);


-- --------------------------------------------------------------------
-- 2.7 product_schema.inventories
-- --------------------------------------------------------------------
INSERT INTO product_schema.inventories (
    id,
    variant_uuid,
    stock_quantity,
    reserved_quantity,
    sold_quantity,
    updated_at
)
SELECT
    gen_random_uuid() AS id,
    pv.id AS variant_uuid,
    pv.stock_quantity AS stock_quantity,
    0 AS reserved_quantity,
    0 AS sold_quantity,
    NOW() - (random() * INTERVAL '30 days') AS updated_at
FROM product_schema.product_variants pv;


-- --------------------------------------------------------------------
-- 2.8 product_schema.product_images
--     - primary image for each catalog and vendor_product business id
-- --------------------------------------------------------------------

-- Catalog images
INSERT INTO product_schema.product_images (
    image_id,
    owner_id,
    owner_type,
    url,
    is_primary,
    created_at
)
SELECT
    gen_random_uuid() AS image_id,
    pc.catalog_id AS owner_id,
    'CATALOG' AS owner_type,
    'https://img.example.com/catalog/' || pc.catalog_id || '/main.jpg' AS url,
    TRUE AS is_primary,
    NOW() - (random() * INTERVAL '365 days') AS created_at
FROM product_schema.product_catalog pc;

-- Vendor product images (business id)
INSERT INTO product_schema.product_images (
    image_id,
    owner_id,
    owner_type,
    url,
    is_primary,
    created_at
)
SELECT
    gen_random_uuid() AS image_id,
    vp.vendor_product_id AS owner_id,
    'VENDOR_PRODUCT' AS owner_type,
    'https://img.example.com/vp/' || vp.vendor_product_id || '/main.jpg' AS url,
    TRUE AS is_primary,
    NOW() - (random() * INTERVAL '365 days') AS created_at
FROM product_schema.vendor_products vp;


-- ====================================================================
-- 3. ORDER DATA (linked to USER + PRODUCT)
-- ====================================================================

-- --------------------------------------------------------------------
-- 3.1 Carts  (~50% users have a cart)
-- --------------------------------------------------------------------
INSERT INTO order_schema.carts (
    id,
    user_id,
    created_at
)
SELECT
    gen_random_uuid() AS id,
    up.user_id,
    NOW() - (random() * INTERVAL '60 days') AS created_at
FROM user_schema.user_profiles up
WHERE random() < 0.5;


-- --------------------------------------------------------------------
-- 3.2 Cart items  (1 item per cart, based on real product variants)
-- --------------------------------------------------------------------
INSERT INTO order_schema.cart_items (
    id,
    cart_uuid,
    variant_id,
    vendor_id,
    vendor_product_id,
    vendor_product_name,
    vendor_product_url,
    quantity,
    price,
    created_at
)
SELECT
    gen_random_uuid() AS id,
    c.id AS cart_uuid,
    prod.variant_id,
    prod.vendor_id,
    prod.vendor_product_id,
    prod.product_name,
    COALESCE(prod.image_url, 'https://img.example.com/vp/' || prod.vendor_product_id) AS vendor_product_url,
    prod.qty,
    prod.price,
    NOW() - (random() * INTERVAL '30 days') AS created_at
FROM order_schema.carts c
CROSS JOIN LATERAL (
    SELECT
        vp.vendor_id,
        vp.vendor_product_id,
        COALESCE(vp.name, pc.name) AS product_name,
        pv.variant_id,
        pv.price,
        1 + (FLOOR(random()*4))::int AS qty,
        (
            SELECT url
            FROM product_schema.product_images pi
            WHERE pi.owner_id = vp.vendor_product_id
              AND pi.owner_type = 'VENDOR_PRODUCT'
              AND pi.is_primary = TRUE
            ORDER BY pi.created_at DESC
            LIMIT 1
        ) AS image_url
    FROM product_schema.product_variants pv
    JOIN product_schema.vendor_products vp
        ON vp.id = pv.vendor_product_uuid
    JOIN product_schema.product_catalog pc
        ON pc.id = vp.catalog_uuid
    ORDER BY random()
    LIMIT 1
) AS prod;


-- --------------------------------------------------------------------
-- 3.3 Orders  (~30% users, up to 3 orders each)
-- --------------------------------------------------------------------

INSERT INTO order_schema.orders (
    id,
    order_code,
    user_id,
    vendor_id,
    total_amount,
    status,
    payment_status,
    created_at,
    updated_at
)
SELECT
    gen_random_uuid() AS id,
    'ORD-' || TO_CHAR(NOW(), 'YYYYMMDD') || '-' ||
        LPAD((ROW_NUMBER() OVER ())::text, 8, '0') AS order_code,
    up.user_id,
    vp.vendor_id,
    0::numeric(15,2) AS total_amount,
    (ARRAY[0,1,2,3])[FLOOR(random()*4)::int + 1] AS status,
    (ARRAY[0,1])[FLOOR(random()*2)::int + 1] AS payment_status,
    NOW() - (random() * INTERVAL '60 days') AS created_at,
    NOW() AS updated_at
FROM user_schema.user_profiles up
CROSS JOIN generate_series(1, 3) AS g(order_slot)
CROSS JOIN LATERAL (
    SELECT vendor_id
    FROM product_schema.vendor_products
    ORDER BY random()
    LIMIT 1
) AS vp
WHERE random() < 0.1;  -- roughly 30% users *avg*


-- --------------------------------------------------------------------
-- 3.4 Order items  (1 item per order, based on vendor_id)
-- --------------------------------------------------------------------
INSERT INTO order_schema.order_items (
    id,
    order_uuid,
    product_id,
    variant_id,
    product_name,
    quantity,
    price,
    subtotal
)
SELECT
    gen_random_uuid() AS id,
    o.id AS order_uuid,
    prod.vendor_product_id AS product_id,
    prod.variant_id,
    prod.product_name,
    prod.qty AS quantity,
    prod.price,
    prod.qty * prod.price AS subtotal
FROM order_schema.orders o
CROSS JOIN LATERAL (
    SELECT
        vp.vendor_product_id,
        COALESCE(vp.name, pc.name) AS product_name,
        pv.variant_id,
        pv.price,
        1 + (FLOOR(random()*5))::int AS qty
    FROM product_schema.vendor_products vp
    JOIN product_schema.product_catalog pc
        ON pc.id = vp.catalog_uuid
    JOIN product_schema.product_variants pv
        ON pv.vendor_product_uuid = vp.id
    WHERE vp.vendor_id = o.vendor_id
    ORDER BY random()
    LIMIT 1
) AS prod;


-- --------------------------------------------------------------------
-- 3.5 Update total_amount from order_items
-- --------------------------------------------------------------------
UPDATE order_schema.orders o
SET total_amount = s.sum_subtotal
FROM (
    SELECT
        order_uuid,
        SUM(subtotal) AS sum_subtotal
    FROM order_schema.order_items
    GROUP BY order_uuid
) AS s
WHERE o.id = s.order_uuid;


-- --------------------------------------------------------------------
-- 3.6 Order history (1–2 entries per order)
-- --------------------------------------------------------------------
INSERT INTO order_schema.order_history (
    id,
    order_uuid,
    status,
    note,
    created_at
)
SELECT
    gen_random_uuid() AS id,
    o.id AS order_uuid,
    o.status,
    'Order created' AS note,
    o.created_at
FROM order_schema.orders o;

INSERT INTO order_schema.order_history (
    id,
    order_uuid,
    status,
    note,
    created_at
)
SELECT
    gen_random_uuid() AS id,
    o.id AS order_uuid,
    o.status,
    (ARRAY['Payment confirmed', 'Order shipped', 'Order delivered'])[FLOOR(random()*3)::int + 1] AS note,
    o.created_at + (random() * INTERVAL '10 days') AS created_at
FROM order_schema.orders o
WHERE random() < 0.8;

-- ====================================================================
-- END OF SEED SCRIPT
-- ====================================================================
