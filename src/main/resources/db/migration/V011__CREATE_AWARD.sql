CREATE TABLE award
(
    id         BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    award_name VARCHAR(255) NOT NULL,
    category   VARCHAR(255),
    year       INT          NOT NULL
) ENGINE = InnoDB