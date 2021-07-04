CREATE TABLE book_award
(
    book_id   BIGINT NOT NULL,
    award_id  BIGINT NOT NULL,
    PRIMARY KEY (book_id, award_id),
    FOREIGN KEY (book_id)   REFERENCES book (id),
    FOREIGN KEY (award_id)  REFERENCES award (id)
) ENGINE = InnoDB