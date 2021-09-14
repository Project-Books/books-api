BEGIN TRANSACTION;
do
$$
declare
    penguin int;
    science_genre int;
    english int;
    paperback int;
begin
    SELECT id
    INTO penguin
    FROM publisher
    WHERE name = 'Penguin';

    SELECT id
    INTO science_genre
    FROM genre
    WHERE name = 'Science';

    SELECT id
    INTO english
    FROM language
    WHERE name = 'English';

    SELECT id
    INTO paperback
    FROM publishing_format
    WHERE type = 'Paperback';

    INSERT INTO book (title, isbn13, genre, year_of_publication, blurb, publisher, isbn10, cover_id, language, publishing_format)
    VALUES (
        'A Brief History of Time', '9780857501004',
         science_genre,
         2011,
         'Was there a beginning of time? Could time run backwards? Is the universe infinite or does it have boundaries?',
         penguin,
         NULL,
         1,
         english,
         paperback
    );
end;
$$;
COMMIT TRANSACTION;