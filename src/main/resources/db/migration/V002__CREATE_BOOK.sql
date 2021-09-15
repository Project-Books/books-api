CREATE TABLE IF NOT EXISTS book (
    id                  SERIAL       NOT NULL PRIMARY KEY,
    title               VARCHAR(255) NOT NULL,
    languageName        INTEGER      NOT NULL,
    isbn13              VARCHAR(255),
    genre               INTEGER      NOT NULL,
    year_of_publication INTEGER,
    blurb               VARCHAR(255) NOT NULL,
    published_by        INTEGER,
    formatType          INTEGER      NOT NULL
);
