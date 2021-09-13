CREATE TABLE format (
    id   SERIAL      NOT NULL PRIMARY KEY,
    type VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO format (type) VALUES ('eBook');
INSERT INTO format (type) VALUES ('Hardcover');
INSERT INTO format (type) VALUES ('Paperback');

-- ALTER TABLE book
-- ADD COLUMN format INTEGER NOT NULL REFERENCES format(id);
