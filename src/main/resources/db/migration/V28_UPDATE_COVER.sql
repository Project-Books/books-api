ALTER TABLE cover
DROP COLUMN IF EXISTS small_file_type;

ALTER TABLE cover
DROP COLUMN IF EXISTS medium_file_type;

ALTER TABLE cover
DROP COLUMN IF EXISTS large_file_type;

ALTER TABLE cover
DROP COLUMN IF EXISTS book;

ALTER TABLE cover
ADD COLUMN small_url VARCHAR;

ALTER TABLE cover
ADD COLUMN medium_url VARCHAR;

ALTER TABLE cover
ADD COLUMN large_url VARCHAR;
