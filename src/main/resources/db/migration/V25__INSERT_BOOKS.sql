BEGIN TRANSACTION;
do
$$
declare
    penguin_publisher_id int;

    science_genre int;
    historical_fiction int;

    english int;
    paperback int;

    hawking int;
    towles int;
begin
    SELECT id
    INTO penguin_publisher_id
    FROM publisher
    WHERE name = 'Penguin';

    SELECT id
    INTO science_genre
    FROM genre
    WHERE name = 'Science';

    SELECT id
    INTO english
    FROM lang
    WHERE name = 'English';

    SELECT id
    INTO paperback
    FROM publishing_format
    WHERE format_name = 'Paperback';

    INSERT INTO book (
      title,
      isbn13,
      genre_id,
      year_of_publication,
      blurb,
      publisher,
      isbn10,
      cover_id,
      lang_id,
      publishing_format_id
    )
    VALUES (
        'A Brief History of Time',
        '9780857501004',
         science_genre,
         2011,
         'Was there a beginning of time? Could time run backwards? Is the universe infinite or does it have boundaries?',
         penguin_publisher_id,
         NULL,
         1,
         english,
         paperback
    );

    SELECT id
    INTO historical_fiction
    FROM genre
    WHERE name = 'Historical fiction';

    INSERT INTO book (
        title,
        isbn13,
        genre_id,
        year_of_publication,
        blurb,
        publisher,
        isbn10,
        cover_id,
        lang_id,
        publishing_format_id
    )
    VALUES (
       'A Gentleman in Moscow',
       '9780099558781',
       historical_fiction,
       2017,
       '',
       penguin_publisher_id,
       NULL,
       2,
       english,
       paperback
   );

    SELECT id
    INTO hawking
    FROM author
    WHERE full_name = 'Stephen Hawking';

    SELECT id
    INTO towles
    FROM author
    WHERE full_name = 'Amor Towles';

    INSERT INTO book_author (book_id, author_id) VALUES (1, hawking);
    INSERT INTO book_author (book_id, author_id) VALUES (2, towles);

    INSERT INTO publisher_book (publisher_id, book_id) VALUES (penguin_publisher_id, 1);
    INSERT INTO publisher_book (publisher_id, book_id) VALUES (penguin_publisher_id, 2);
end;
$$;
COMMIT TRANSACTION;