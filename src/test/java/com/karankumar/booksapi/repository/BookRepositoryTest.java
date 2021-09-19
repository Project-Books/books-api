/*
 * Copyright (C) 2021  Karan Kumar
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package com.karankumar.booksapi.repository;

import com.karankumar.booksapi.annotations.DataJpaIntegrationTest;
import com.karankumar.booksapi.model.Author;
import com.karankumar.booksapi.model.Book;
import com.karankumar.booksapi.model.Publisher;
import com.karankumar.booksapi.model.PublishingFormat;
import com.karankumar.booksapi.model.genre.Genre;
import com.karankumar.booksapi.model.genre.GenreName;
import com.karankumar.booksapi.model.language.Lang;
import com.karankumar.booksapi.model.language.LanguageName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DataJpaIntegrationTest
@DisplayName("BookRepository should")
class BookRepositoryTest {
    private static final String ISBN = "978-3-16-148410-0";
    private static final String TITLE = "Harry Potter";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final FormatRepository formatRepository;
    private final LanguageRepository languageRepository;
    private final GenreRepository genreRepository;

    @Autowired
    BookRepositoryTest(BookRepository bookRepository, AuthorRepository authorRepository,
                       PublisherRepository publisherRepository, FormatRepository formatRepository,
                       LanguageRepository languageRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.formatRepository = formatRepository;
        this.languageRepository = languageRepository;
        this.genreRepository = genreRepository;
    }

    @BeforeEach
    void setUp() {
        authorRepository.deleteAll();
        bookRepository.deleteAll();

        publisherRepository.deleteAll();
        formatRepository.deleteAll();
        languageRepository.deleteAll();
        genreRepository.deleteAll();
    }

    @Test
    void findSavedBooks() {
        // given
        PublishingFormat bookFormat = new PublishingFormat();
        formatRepository.save(bookFormat);
        Lang language = new Lang(LanguageName.ENGLISH);
        languageRepository.save(language);
        Genre genre = new Genre(GenreName.CHILDREN);
        genreRepository.save(genre);

        Book book = new Book(
                "97 Things Every Java Programmer Should Know",
                language,
                "Sample blurb value",
                genre,
                bookFormat
        );
        Author author = new Author("Name", Set.of(book));
        book.setAuthors(Set.of(author));
        bookRepository.save(book);
        authorRepository.save(author);

        // when
        List<Book> result = bookRepository.findAllBooks();

        // then
        assertSoftly(softly -> {
            softly.assertThat(result.size()).isOne();
            softly.assertThat(result).containsExactlyInAnyOrder(book);
        });
    }

    @Test
    void findBookByIsbn() {
        // given
        Book book = createBookWithIsbn13();
        bookRepository.save(book);

        // when
        Book result = bookRepository.findBookByIsbn13(ISBN);

        // then
        assertThat(result).isEqualTo(book);
    }

    private Book createBookWithIsbn13() {
        PublishingFormat bookFormat = new PublishingFormat();
        formatRepository.save(bookFormat);
        Lang language = new Lang(LanguageName.ENGLISH);
        languageRepository.save(language);
        Genre genre = new Genre(GenreName.SATIRE);
        genreRepository.save(genre);

        Book book = new Book(
                "Game of APIs",
                language,
                "",
                genre,
                bookFormat
        );
        book.setIsbn13(ISBN);
        return book;
    }

    @Test
    void findByAuthor() {
        // given
        Book book = createBookWithIsbn13();
        bookRepository.save(book);
        String authorName = "Kevlin Henney";
        Author author = new Author(authorName, new HashSet<>());
        author.addBook(book);
        authorRepository.save(author);

        // when
        List<Book> result = bookRepository.findByAuthor(authorName);

        // then
        assertSoftly(softly -> {
            softly.assertThat(result.size()).isOne();
            softly.assertThat(result.get(0)).isEqualTo(book);
        });
    }

    @Test
    void findByPublisher() {
        // given
        Book book = createBookWithIsbn13();
        String publisherName = "Bloomsbury";
        Publisher publisher = new Publisher(publisherName);
        publisher.addBook(book);
        publisherRepository.save(publisher);
        bookRepository.save(book);

        // when
        List<Book> result = bookRepository.findByPublisher(publisherName);

        // then
        assertSoftly(softly -> {
            softly.assertThat(result.size()).isOne();
            softly.assertThat(result.get(0)).isEqualTo(book);
        });
    }

    @Test
    void findBookByTitle() {
        // given
        PublishingFormat bookFormat = new PublishingFormat();
        formatRepository.save(bookFormat);
        Lang language = new Lang(LanguageName.ENGLISH);
        languageRepository.save(language);
        Genre genre = new Genre(GenreName.ART);
        genreRepository.save(genre);
        Book book = new Book(
                TITLE,
                language,
                "",
                genre,
                bookFormat
        );
        bookRepository.save(book);

        // when
        Book result = bookRepository.findByTitleIgnoreCase(TITLE);

        // then
        assertThat(result).isEqualTo(book);
    }

    @Test
    void findBookByTitleCaseInsensitive() {
        // given
        PublishingFormat bookFormat = new PublishingFormat();
        formatRepository.save(bookFormat);
        Lang language = new Lang(LanguageName.AFRIKAANS);
        languageRepository.save(language);
        Genre genre = new Genre(GenreName.MYSTERY);
        genreRepository.save(genre);
        Book book = new Book(
                TITLE,
                language,
                "",
                genre,
                bookFormat
        );
        bookRepository.save(book);

        // when
        Book result = bookRepository.findByTitleIgnoreCase(TITLE.toLowerCase());

        // then
        assertThat(result).isEqualTo(book);
    }
}
