CREATE TABLE book_author
(
    book_id   BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id)   REFERENCES book (id),
    FOREIGN KEY (author_id) REFERENCES author (id)
)
