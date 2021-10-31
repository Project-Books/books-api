BEGIN TRANSACTION;
do
$$
declare
    penguin_publisher_id int;
    simon_and_schuster int;
    little_brown_publisher int;

    science_genre int;
    historical_fiction int;
    thriller int;
    crime_genre int;

    english int;
    paperback int;

    hawking int;
    towles int;
    dan_brown int;
    galbraith int;
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
       english,
       paperback
    );

    SELECT id
    INTO simon_and_schuster
    FROM publisher
    WHERE name = 'Simon and Schuster';

    SELECT id
    INTO thriller
    FROM genre
    WHERE name = 'Thriller';

    INSERT INTO book (
        title,
        isbn13,
        genre_id,
        year_of_publication,
        blurb,
        publisher,
        isbn10,
        lang_id,
        publishing_format_id
    )
    VALUES (
       'Angels & Demons',
       '9780743493468',
       thriller,
       2006,
       'First book in the Robert Langdon series',
       simon_and_schuster,
       NULL,
       english,
       paperback
    );

    SELECT id
    INTO crime_genre
    FROM genre
    WHERE name = 'Crime';

    SELECT id
    INTO little_brown_publisher
    FROM publisher
    WHERE name = 'Little, Brown Book Group';

    INSERT INTO book (
        title,
        isbn13,
        genre_id,
        year_of_publication,
        blurb,
        publisher,
        isbn10,
        lang_id,
        publishing_format_id
    )
    VALUES (
       'Career of Evil',
       '9780751563597',
       crime_genre,
       2015,
       'When a mysterious package is delivered to Robin Ellacott, she is horrified to discover that it contains a ' ||
       'woman’s severed leg. \n\nHer boss, private detective Cormoran Strike, is less surprised but no less alarmed. ' ||
       'There are four people from his past who he thinks could be responsible – and Strike knows that any one of ' ||
       'them is capable of sustained and unspeakable brutality. \n\nWith the police focusing on the one suspect Strike is ' ||
       'increasingly sure is not the perpetrator, he and Robin take matters into their own hands, and delve into ' ||
       'the dark and twisted worlds of the other three men. But as more horrendous acts occur, time is running out ' ||
       'for the two of them… ' ||
       '\n\nA fiendishly clever mystery with unexpected twists around every corner, Career of Evil is also a gripping ' ||
       'story of a man and a woman at a crossroads in their personal and professional lives. You will not be able ' ||
       'to put this book down.',
       little_brown_publisher,
       NULL,
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

    SELECT id
    INTO dan_brown
    FROM author
    WHERE full_name = 'Dan Brown';

    SELECT id
    INTO galbraith
    FROM author
    WHERE full_name = 'Robert Galbraith';

    INSERT INTO book_author (book_id, author_id) VALUES (1, hawking);
    INSERT INTO book_author (book_id, author_id) VALUES (2, towles);
    INSERT INTO book_author (book_id, author_id) VALUES (3, dan_brown);
    INSERT INTO book_author (book_id, author_id) VALUES (4, galbraith);

    INSERT INTO publisher_book (publisher_id, book_id) VALUES (penguin_publisher_id, 1);
    INSERT INTO publisher_book (publisher_id, book_id) VALUES (penguin_publisher_id, 2);
    INSERT INTO publisher_book (publisher_id, book_id) VALUES (simon_and_schuster, 3);
    INSERT INTO publisher_book (publisher_id, book_id) VALUES (little_brown_publisher, 4);
end;
$$;
COMMIT TRANSACTION;

