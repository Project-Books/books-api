ALTER TABLE book
ADD COLUMN book_cover_id INTEGER REFERENCES book_cover(id);

