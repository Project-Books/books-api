CREATE TABLE IF NOT EXISTS cover (
    id               SERIAL  NOT NULL PRIMARY KEY,
    small_file_type  INTEGER REFERENCES cover_file_type(id),
    medium_file_type INTEGER REFERENCES cover_file_type(id),
    large_file_type  INTEGER REFERENCES cover_file_type(id),
    book             INTEGER REFERENCES book(id)
);
