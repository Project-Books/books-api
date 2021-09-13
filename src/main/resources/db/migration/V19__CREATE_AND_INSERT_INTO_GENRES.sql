CREATE TABLE genre (
    id    SERIAL     NOT NULL PRIMARY KEY,
    name VARCHAR(30) NOT NULL UNIQUE
);

INSERT INTO genre (name) VALUES ('Adventure');
INSERT INTO genre (name) VALUES ('Anthology');
INSERT INTO genre (name) VALUES ('Art');
INSERT INTO genre (name) VALUES ('Autobiography');
INSERT INTO genre (name) VALUES ('Biography');
INSERT INTO genre (name) VALUES ('Business');
INSERT INTO genre (name) VALUES ('Children');
INSERT INTO genre (name) VALUES ('Classic');
INSERT INTO genre (name) VALUES ('Cookbook');
INSERT INTO genre (name) VALUES ('Comedy');
INSERT INTO genre (name) VALUES ('Comics');
INSERT INTO genre (name) VALUES ('Crime');
INSERT INTO genre (name) VALUES ('Drama');
INSERT INTO genre (name) VALUES ('Essay');
INSERT INTO genre (name) VALUES ('Fantasy');
INSERT INTO genre (name) VALUES ('Fable');
INSERT INTO genre (name) VALUES ('Fairy tale');
INSERT INTO genre (name) VALUES ('Fan fiction');
INSERT INTO genre (name) VALUES ('Fiction');
INSERT INTO genre (name) VALUES ('Health');
INSERT INTO genre (name) VALUES ('History');
INSERT INTO genre (name) VALUES ('Historical fiction');
INSERT INTO genre (name) VALUES ('Horror');
INSERT INTO genre (name) VALUES ('Memoir');
INSERT INTO genre (name) VALUES ('Mystery');
INSERT INTO genre (name) VALUES ('Non-fiction');
INSERT INTO genre (name) VALUES ('Periodical');
INSERT INTO genre (name) VALUES ('Philosophy');
INSERT INTO genre (name) VALUES ('Poetry');
INSERT INTO genre (name) VALUES ('Psychology');
INSERT INTO genre (name) VALUES ('Reference');
INSERT INTO genre (name) VALUES ('Religion');
INSERT INTO genre (name) VALUES ('Romance');
INSERT INTO genre (name) VALUES ('Satire');
INSERT INTO genre (name) VALUES ('Science');
INSERT INTO genre (name) VALUES ('Science fiction');
INSERT INTO genre (name) VALUES ('Self Help');
INSERT INTO genre (name) VALUES ('Short story');
INSERT INTO genre (name) VALUES ('Sports');
INSERT INTO genre (name) VALUES ('Thriller');
INSERT INTO genre (name) VALUES ('Travel');
INSERT INTO genre (name) VALUES ('Young adult');
