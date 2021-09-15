CREATE TABLE publishing_format (
    id          SERIAL      NOT NULL PRIMARY KEY,
    format_name VARCHAR(30) NOT NULL UNIQUE
);

INSERT INTO publishing_format (format_name) VALUES ('eBook');
INSERT INTO publishing_format (format_name) VALUES ('Hardcover');
INSERT INTO publishing_format (format_name) VALUES ('Paperback');
INSERT INTO publishing_format (format_name) VALUES ('Audiobook (unabridged)');
INSERT INTO publishing_format (format_name) VALUES ('Audiobook (abridged)');
