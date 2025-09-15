CREATE SCHEMA IF NOT EXISTS product_schema;

CREATE TABLE IF NOT EXISTS product_schema.product_catalog (
    id UUID PRIMARY KEY,
    catalog_id VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    brand VARCHAR(255),
    attributes_json JSONB,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS product_schema.product_images (
    image_id UUID PRIMARY KEY,
    catalog_uuid UUID NOT NULL REFERENCES product_schema.product_catalog(id) ON DELETE CASCADE,
    url VARCHAR(500) NOT NULL,
    is_primary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX IF NOT EXISTS idx_product_images_catalog_uuid ON product_schema.product_images(catalog_uuid);

CREATE TABLE IF NOT EXISTS product_schema.categories (
    id UUID PRIMARY KEY,
    category_id VARCHAR(20) UNIQUE NOT NULL,
    parent_id UUID REFERENCES product_schema.categories(id) ON DELETE SET NULL,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS product_schema.product_categories (
    catalog_uuid UUID NOT NULL REFERENCES product_schema.product_catalog(id) ON DELETE CASCADE,
    category_uuid UUID NOT NULL REFERENCES product_schema.categories(id) ON DELETE CASCADE,
    PRIMARY KEY (catalog_uuid, category_uuid)
);

CREATE TABLE IF NOT EXISTS product_schema.vendor_products (
    id UUID PRIMARY KEY,
    vendor_product_id VARCHAR(20) UNIQUE NOT NULL,
    vendor_id UUID NOT NULL,
    catalog_uuid UUID NOT NULL REFERENCES product_schema.product_catalog(id) ON DELETE CASCADE,
    price DECIMAL(15,2) NOT NULL CHECK (price >= 0),
    stock_quantity INT NOT NULL CHECK (stock_quantity >= 0),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE UNIQUE INDEX IF NOT EXISTS idx_vendor_products_vendor_product_id ON product_schema.vendor_products(vendor_product_id);
CREATE INDEX IF NOT EXISTS idx_vendor_products_vendor_id ON product_schema.vendor_products(vendor_id);
CREATE INDEX IF NOT EXISTS idx_vendor_products_catalog_uuid ON product_schema.vendor_products(catalog_uuid);

CREATE TABLE IF NOT EXISTS product_schema.vendor_product_images (
    image_id UUID PRIMARY KEY,
    vendor_product_uuid UUID NOT NULL REFERENCES product_schema.vendor_products(id) ON DELETE CASCADE,
    url VARCHAR(500) NOT NULL,
    is_primary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX IF NOT EXISTS idx_vendor_product_images_vendor_product_uuid ON product_schema.vendor_product_images(vendor_product_uuid);

CREATE TABLE IF NOT EXISTS product_schema.product_variants (
    id UUID PRIMARY KEY,
    variant_id VARCHAR(20) UNIQUE NOT NULL,
    vendor_product_uuid UUID NOT NULL REFERENCES product_schema.vendor_products(id) ON DELETE CASCADE,
    sku VARCHAR(100),
    attributes_json JSONB,
    price DECIMAL(15,2) CHECK (price >= 0),
    stock_quantity INT CHECK (stock_quantity >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX IF NOT EXISTS idx_product_variants_vendor_product_uuid ON product_schema.product_variants(vendor_product_uuid);

INSERT INTO product_schema.product_catalog (id, catalog_id, name, description, brand, attributes_json) SELECT gen_random_uuid(), 'CAT-' || gs, 'Product ' || gs, 'Description for product ' || gs, 'Brand ' || (gs % 5 + 1), '{"color": "red", "size": "M"}'::jsonb FROM generate_series(1,50) gs ON CONFLICT (catalog_id) DO NOTHING;
INSERT INTO product_schema.categories (id, category_id, parent_id, name, slug, description) SELECT gen_random_uuid(), 'CATG-' || gs, NULL, 'Category ' || gs, 'category-' || gs, 'Description for category ' || gs FROM generate_series(1,50) gs ON CONFLICT (slug) DO NOTHING;
INSERT INTO product_schema.product_categories (catalog_uuid, category_uuid) SELECT (SELECT id FROM product_schema.product_catalog ORDER BY random() LIMIT 1), (SELECT id FROM product_schema.categories ORDER BY random() LIMIT 1) FROM generate_series(1,50) gs ON CONFLICT DO NOTHING;
INSERT INTO product_schema.product_images (image_id, catalog_uuid, url, is_primary) SELECT gen_random_uuid(), (SELECT id FROM product_schema.product_catalog ORDER BY random() LIMIT 1), 'https://example.com/product_img_' || gs || '.jpg', (gs % 5 = 0) FROM generate_series(1,50) gs ON CONFLICT DO NOTHING;
INSERT INTO product_schema.vendor_products (id, vendor_product_id, vendor_id, catalog_uuid, price, stock_quantity, status) SELECT gen_random_uuid(), 'VPROD-' || gs, gen_random_uuid(), (SELECT id FROM product_schema.product_catalog ORDER BY random() LIMIT 1), (random() * 100)::numeric(10,2), (random() * 100)::int, 'ACTIVE' FROM generate_series(1,50) gs ON CONFLICT (vendor_product_id) DO NOTHING;
INSERT INTO product_schema.vendor_product_images (image_id, vendor_product_uuid, url, is_primary) SELECT gen_random_uuid(), (SELECT id FROM product_schema.vendor_products ORDER BY random() LIMIT 1), 'https://example.com/vendor_product_img_' || gs || '.jpg', (gs % 5 = 0) FROM generate_series(1,50) gs ON CONFLICT DO NOTHING;
INSERT INTO product_schema.product_variants (id, variant_id, vendor_product_uuid, sku, attributes_json, price, stock_quantity) SELECT gen_random_uuid(), 'VAR-' || gs, (SELECT id FROM product_schema.vendor_products ORDER BY random() LIMIT 1), 'SKU-' || gs, '{"color": "blue", "size": "L"}'::jsonb, (random() * 120)::numeric(10,2), (random() * 200)::int FROM generate_series(1,50) gs ON CONFLICT (variant_id) DO NOTHING;