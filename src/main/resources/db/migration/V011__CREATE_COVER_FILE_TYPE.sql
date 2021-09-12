CREATE TABLE IF NOT EXISTS cover_file_type (
    id         SERIAL        NOT NULL PRIMARY KEY,
    file_type  VARCHAR(255)
);

INSERT INTO cover_file_type (file_type) VALUES ('jpg');
INSERT INTO cover_file_type (file_type) VALUES ('png');
