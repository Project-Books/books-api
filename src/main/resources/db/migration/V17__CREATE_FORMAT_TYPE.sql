CREATE TABLE publishing_format (
    id   SERIAL      NOT NULL PRIMARY KEY,
    type VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO publishing_format (type) VALUES ('eBook');
INSERT INTO publishing_format (type) VALUES ('Hardcover');
INSERT INTO publishing_format (type) VALUES ('Paperback');
