ALTER TABLE book
ADD CONSTRAINT book_genre_fkey
FOREIGN KEY (genre) REFERENCES genre(id);
