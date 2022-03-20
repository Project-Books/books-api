-- For local development only

BEGIN TRANSACTION;
do
$$
declare
    brief_history_id int;
    gentleman_in_moscow_id int;
    angels_and_demons_id int;
    career_of_evil_id int;
    piranesi_id int;
begin
    SELECT id
    INTO brief_history_id
    FROM cover
    where medium_url = 'https://penguin.co.uk/content/dam/prh/books/100/1008130/9780857501004.jpg.transform/PRHDesktopWide_small/image.jpg';

    SELECT id
    INTO gentleman_in_moscow_id
    FROM cover
    WHERE medium_url = 'https://penguin.co.uk/content/dam/prh/books/109/1092546/9780099558781.jpg.transform/PRHDesktopWide_small/image.jpg';

    SELECT id
    INTO angels_and_demons_id
    FROM cover
    WHERE small_url = 'https://danbrown.com/wp-content/themes/danbrown/images/db/covers/ad.jpg';

    SELECT id
    INTO career_of_evil_id
    FROM cover
    WHERE small_url = 'https://robert-galbraith.com/wp-content/uploads/2020/08/career-of-evil-cover.jpg';

    SELECT id
    INTO piranesi_id
    FROM cover
    WHERE small_url = 'https://res.cloudinary.com/bloomsbury-atlas/image/upload/w_148,c_scale/jackets/9781526622433.jpg';

    UPDATE book
    SET cover_id = brief_history_id
    WHERE title = 'A Brief History of Time';

    UPDATE book
    SET cover_id = gentleman_in_moscow_id
    WHERE title = 'A Gentleman in Moscow';

    UPDATE book
    SET cover_id = angels_and_demons_id
    WHERE title = 'Angels & Demons';

    UPDATE book
    SET cover_id = career_of_evil_id
    WHERE title = 'Career of Evil';

    UPDATE book
    SET cover_id = piranesi_id
    WHERE title = 'Piranesi';

end;
$$;
COMMIT TRANSACTION
