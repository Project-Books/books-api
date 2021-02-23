CREATE TABLE book
(
    id                  BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    language            INTEGER      NOT NULL,
    isbn13              VARCHAR(255) NOT NULL,
    genre               INTEGER      NOT NULL,
    year_of_publication INTEGER      NOT NULL,
    blurb               VARCHAR(255) NOT NULL,
    publisher           INTEGER      NOT NULL,
    format              INTEGER      NOT NULL
) ENGINE = InnoDB