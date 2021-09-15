ALTER TABLE book
DROP COLUMN IF EXISTS formattype;

ALTER TABLE book
ADD COLUMN IF NOT EXISTS publishing_format_id INTEGER NOT NULL REFERENCES publishing_format(id);
