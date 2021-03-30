ALTER TABLE publisher_book
DROP FOREIGN KEY FK6buft2dj1d6ig7hekbx7c0ysp,
ADD CONSTRAINT publisher_book_publisher_id FOREIGN KEY (publisher_id) REFERENCES publisher (id);

ALTER TABLE publisher_book
DROP FOREIGN KEY FKnro4ab7u1j42osd4sehbkptrr,
ADD CONSTRAINT publisher_book_book_id FOREIGN KEY (book_id) REFERENCES book (id);
