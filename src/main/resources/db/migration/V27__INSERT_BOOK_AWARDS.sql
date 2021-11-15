BEGIN TRANSACTION;
do
$$
    declare
        bloomsbury_publishing  int;
        harper_collins         int;

        fantasy                int;
        historical_fiction     int;

        english                int;

        hardcover              int;

        clarke                 int;
        mantel                 int;

        womens_prize           int;
        booker_prize           int;

    begin

        SELECT id
        INTO english
        FROM lang
        WHERE name = 'English';

        SELECT id
        INTO fantasy
        FROM genre
        WHERE name = 'Fantasy';

        SELECT id
        INTO historical_fiction
        FROM genre
        WHERE name = 'Historical fiction';

        SELECT id
        INTO bloomsbury_publishing
        FROM publisher
        WHERE name = 'Bloomsbury Publishing';

        SELECT id
        INTO harper_collins
        FROM publisher
        WHERE name = 'Harper Collins';

        SELECT id
        INTO hardcover
        FROM publishing_format
        WHERE format_name = 'Hardcover';

        INSERT INTO book (title,
                          isbn13,
                          genre_id,
                          year_of_publication,
                          blurb,
                          publisher,
                          isbn10,
                          lang_id,
                          publishing_format_id)
        VALUES ('Piranesi',
                '9781526622426',
                fantasy,
                2020,
                'Piranesi''s house is no ordinary building: its rooms are infinite, its corridors endless, its walls are lined ' ||
                'with thousands upon thousands of statues, each one different from all the others. Within the labyrinth of halls ' ||
                'an ocean is imprisoned; waves thunder up staircases, rooms are flooded in an instant. But Piranesi is not afraid; ' ||
                'he understands the tides as he understands the pattern of the labyrinth itself. He lives to explore the house.' ||
                '\n\n' ||
                'There is one other person in the house—a man called The Other, who visits Piranesi twice a week and asks for help ' ||
                'with research into A Great and Secret Knowledge. But as Piranesi explores, evidence emerges of another person, ' ||
                'and a terrible truth begins to unravel, revealing a world beyond the one Piranesi has always known.',
                bloomsbury_publishing,
                NULL,
                english,
                hardcover);

        INSERT INTO book (title,
                          isbn13,
                          genre_id,
                          year_of_publication,
                          blurb,
                          publisher,
                          isbn10,
                          lang_id,
                          publishing_format_id)
        VALUES ('Wolf Hall',
                '0-00-723018-4',
                historical_fiction,
                2009,
                'England in the 1520s is a heartbeat from disaster. If the king dies without a male heir, ' ||
                'the country could be destroyed by civil war. Henry VIII wants to annul his marriage of twenty years ' ||
                'and marry Anne Boleyn. The pope and most of Europe opposes him. Into this impasse steps ' ||
                'Thomas Cromwell: a wholly original man, a charmer and a bully, both idealist and opportunist, ' ||
                'astute in reading people, and implacable in his ambition. But Henry is volatile: one day tender, one day murderous. ' ||
                'Cromwell helps him break the opposition, but what will be the price of his triumph?',
                harper_collins,
                NULL,
                english,
                hardcover);

        SELECT id
        INTO clarke
        FROM author
        WHERE full_name = 'Susanna Clarke';

        SELECT id
        INTO mantel
        FROM author
        WHERE full_name = 'Hilary Mantel';

        SELECT id
        INTO womens_prize
        FROM award
        WHERE award_name = 'Women''s Prize for Fiction';

        SELECT id
        INTO booker_prize
        FROM award
        WHERE award_name = 'The Man Booker Prize';

        INSERT INTO book_author (book_id, author_id) VALUES (5, clarke);
        INSERT INTO book_author (book_id, author_id) VALUES (6, mantel);

        INSERT INTO publisher_book (publisher_id, book_id) VALUES (bloomsbury_publishing, 5);
        INSERT INTO publisher_book (publisher_id, book_id) VALUES (harper_collins, 6);

        INSERT INTO book_award (book_id, award_id) VALUES (5, womens_prize);
        INSERT INTO book_award (book_id, award_id) VALUES (6, booker_prize);
    end;
$$;
COMMIT TRANSACTION;
