CREATE TABLE publisher_book (
    publisher_id BIGSERIAL NOT NULL,
    book_id      BIGSERIAL NOT NULL,
    PRIMARY KEY (publisher_id, book_id),
    FOREIGN KEY (publisher_id) REFERENCES publisher (id),
    FOREIGN KEY (book_id)      REFERENCES book (id)
)
