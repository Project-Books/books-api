CREATE TABLE IF NOT EXISTS image_file_type (
    id         SERIAL       NOT NULL PRIMARY KEY,
    file_type  VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO image_file_type (file_type) VALUES ('JPG');
INSERT INTO image_file_type (file_type) VALUES ('PNG');
