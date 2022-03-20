ALTER TABLE book
ADD COLUMN IF NOT EXISTS cover_id INTEGER;

-- Only add constraint it it doesn't already exist:
DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'book_cover_id_fkey') THEN
            ALTER TABLE book
            ADD CONSTRAINT book_cover_id_fkey
            FOREIGN KEY (cover_id) REFERENCES cover(id);
        END IF;
    END;
$$;
