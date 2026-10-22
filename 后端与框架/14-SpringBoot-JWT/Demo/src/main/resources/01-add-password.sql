USE learning_app;
ALTER TABLE users ADD COLUMN IF NOT EXISTS password_hash VARCHAR(100) NULL;
