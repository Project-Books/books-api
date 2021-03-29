CREATE TABLE `book_to_book_series_mapping`
(
  `id`              bigint          NOT NULL    AUTO_INCREMENT,
  `serial_number`   int             NOT NULL,
  `book_id`         bigint          NOT NULL,
  `book_series_id`  bigint          NOT NULL,

  PRIMARY KEY (`id`),

  UNIQUE KEY    `UK_bookSeriesId_bookId`        (`book_series_id`,`book_id`),
  UNIQUE KEY    `UK_bookSeriesId_serialNumber`  (`book_series_id`,`serial_number`),
  CONSTRAINT    `FK_book_id`                    FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT    `FK_book_series_id`             FOREIGN KEY (`book_series_id`) REFERENCES `book_series` (`id`)

) ENGINE=InnoDB