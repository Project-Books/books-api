CREATE TABLE publisher_book
(
    publisher_id BIGINT NOT NULL,
    book_id      BIGINT NOT NULL,
    PRIMARY KEY (publisher_id, book_id),
    FOREIGN KEY (publisher_id) REFERENCES publisher (id),
    FOREIGN KEY (book_id)      REFERENCES book (id)
) ENGINE = InnoDB
