CREATE TABLE book_format (
    id   SERIAL      NOT NULL PRIMARY KEY,
    type VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO book_format (type) VALUES ('eBook');
INSERT INTO book_format (type) VALUES ('Hardcover');
INSERT INTO book_format (type) VALUES ('Paperback');
