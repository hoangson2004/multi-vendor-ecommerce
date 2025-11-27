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

CREATE TABLE IF NOT EXISTS product_schema.categories (
    id UUID PRIMARY KEY,
    category_id VARCHAR(20) UNIQUE NOT NULL,
    parent_id UUID REFERENCES product_schema.categories(id) ON DELETE SET NULL,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS product_schema.product_categories (
    catalog_uuid UUID NOT NULL REFERENCES product_schema.product_catalog(id) ON DELETE CASCADE,
    category_uuid UUID NOT NULL REFERENCES product_schema.categories(id) ON DELETE CASCADE,
    PRIMARY KEY (catalog_uuid, category_uuid)
);

CREATE TABLE IF NOT EXISTS product_schema.vendor_products (
    id UUID PRIMARY KEY,
    vendor_product_id VARCHAR(20) UNIQUE NOT NULL,
    vendor_id VARCHAR(20) NOT NULL,
    name VARCHAR(255),
    catalog_uuid UUID NOT NULL REFERENCES product_schema.product_catalog(id) ON DELETE CASCADE,
    price DECIMAL(15,2) NOT NULL CHECK (price >= 0),
    stock_quantity INT NOT NULL CHECK (stock_quantity >= 0),
    status INT NOT NULL DEFAULT 1,
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

CREATE TABLE product_schema.inventories (
    id UUID PRIMARY KEY,
    variant_uuid UUID NOT NULL REFERENCES product_schema.product_variants(id) ON DELETE CASCADE,
    stock_quantity INT DEFAULT 0,
    reserved_quantity INT DEFAULT 0,
    sold_quantity INT DEFAULT 0,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS product_schema.product_images (
    image_id UUID PRIMARY KEY,
    owner_id VARCHAR(20) NOT NULL,
    owner_type VARCHAR(20) NOT NULL,
    url VARCHAR(500) NOT NULL,
    is_primary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_product_images_ownerid ON product_schema.product_images(owner_id);
CREATE INDEX IF NOT EXISTS idx_product_images_owner ON product_schema.product_images(owner_id, owner_type);



