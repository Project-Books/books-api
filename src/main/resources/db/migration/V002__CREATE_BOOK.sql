CREATE TABLE IF NOT EXISTS book
(
    id                  BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title               VARCHAR(255) NOT NULL,
    language            INTEGER      NOT NULL,
    isbn13              VARCHAR(255),
    genre               INTEGER      NOT NULL,
    year_of_publication INTEGER,
    blurb               VARCHAR(255) NOT NULL,
    published_by        INTEGER,
    format              INTEGER      NOT NULL
) ENGINE = InnoDB