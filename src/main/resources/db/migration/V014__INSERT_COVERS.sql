BEGIN TRANSACTION;
do
$$
declare
    jpg_id int;
    png_id int;
begin
    SELECT id
    INTO jpg_id
    FROM image_file_type
    where file_type = 'jpg';

    SELECT id
    INTO png_id
    FROM image_file_type
    WHERE file_type = 'png';

-- 1
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 2
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 3
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 4
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (png_id, NULL, NULL);

-- 5
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 6
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 7
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 8
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 9
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 10
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (png_id, NULL, NULL);

-- 11
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 12
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 13
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 14
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 15
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 16
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 17
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 18
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 19
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 20
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 21
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 22
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (png_id, NULL, NULL);

-- 23
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 24
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 25
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 26
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 26
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 27
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 28
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (png_id, NULL, NULL);

-- 29
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 30
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 31
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 32
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 33
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 34
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 35
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 36
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 37
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 38
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 39
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 40
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (png_id, NULL, NULL);

-- 41
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 42
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 43
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 44
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 45
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 46
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 47
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (png_id, NULL, NULL);

-- 48
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 49
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);

-- 50
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (png_id, NULL, NULL);

-- 51
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (png_id, NULL, NULL);

-- 52
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (png_id, NULL, NULL);

-- 53
    INSERT INTO cover (small_file_type, medium_file_type, large_file_type)
    VALUES (jpg_id, NULL, NULL);
end;
$$;
COMMIT TRANSACTION
