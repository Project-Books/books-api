CREATE TABLE award
(
    id         SERIAL       NOT NULL PRIMARY KEY,
    award_name VARCHAR(255) NOT NULL,
    category   VARCHAR(255),
    year       INTEGER          NOT NULL
);

INSERT INTO award (award_name, category, year) VALUES ('Women''s Prize for Fiction', NULL, 1996);
INSERT INTO award (award_name, category, year) VALUES ('Orwell Book Prize', NULL, 1994);
INSERT INTO award (award_name, category, year) VALUES ('The Portico Prize', NULL, 1985);
INSERT INTO award (award_name, category, year) VALUES ('The Baillie Gifford Prize for Non-Fiction', NULL, 1999);
INSERT INTO award (award_name, category, year) VALUES ('International Dylan Thomas Prize', NULL, 2006);
INSERT INTO award (award_name, category, year) VALUES ('The Man Booker Prize', NULL, 1969);
INSERT INTO award (award_name, category, year) VALUES ('The Man Booker International Prize', NULL, 2004);
INSERT INTO award (award_name, category, year) VALUES ('Nobel Prize in Literature', NULL, 1901);
