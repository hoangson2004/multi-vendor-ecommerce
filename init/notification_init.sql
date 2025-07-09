CREATE SCHEMA IF NOT EXISTS notification_schema;

CREATE TABLE IF NOT EXISTS notification_schema.notifications (
    id UUID PRIMARY KEY,
    user_id UUID,
    type VARCHAR(50),
    message TEXT,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP
);
