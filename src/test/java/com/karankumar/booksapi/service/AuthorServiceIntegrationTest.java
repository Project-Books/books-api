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
package com.karankumar.booksapi.service;

import com.karankumar.booksapi.annotations.DataJpaIntegrationTest;
import com.karankumar.booksapi.model.Author;
import com.karankumar.booksapi.model.Book;
import com.karankumar.booksapi.model.PublishingFormat;
import com.karankumar.booksapi.model.genre.Genre;
import com.karankumar.booksapi.model.genre.GenreName;
import com.karankumar.booksapi.model.language.Lang;
import com.karankumar.booksapi.model.language.LanguageName;
import com.karankumar.booksapi.repository.AuthorRepository;
import com.karankumar.booksapi.repository.BookRepository;
import com.karankumar.booksapi.repository.GenreRepository;
import com.karankumar.booksapi.repository.LanguageRepository;
import com.karankumar.booksapi.repository.NativeQueryRepository;
import com.karankumar.booksapi.repository.PublishingFormatRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DataJpaIntegrationTest
@Import({NativeQueryRepository.class, AuthorService.class})
public class AuthorServiceIntegrationTest {

    private final AuthorService underTest;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final LanguageRepository languageRepository;
    private final PublishingFormatRepository publishingFormatRepository;
    private final BookRepository bookRepository;

    @Autowired
    public AuthorServiceIntegrationTest(AuthorService underTest, AuthorRepository authorRepository,
                                        GenreRepository genreRepository,
                                        LanguageRepository languageRepository,
                                        PublishingFormatRepository publisherRepository,
                                        BookRepository bookRepository) {
        this.underTest = underTest;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.languageRepository = languageRepository;
        this.publishingFormatRepository = publisherRepository;
        this.bookRepository = bookRepository;
    }

    @Test
    public void deleteAuthor_whenAuthorHasNoRelations() {
        // given
        Author author = new Author("Joe Doe", Collections.emptySet());
        authorRepository.save(author);

        // when
        underTest.deleteAuthor(author);

        //then
        Assertions.assertThat(authorRepository.findById(author.getId())).isNotPresent();
    }

    @Test
    public void deleteAuthor_whenAuthorHasExclusiveRelationWithBook() {
        // given
        Book book = createBook("Test book");
        Author author = new Author("Joe Doe",
                new HashSet<>(Collections.singletonList(book)));
        authorRepository.save(author);
        bookRepository.save(book);

        // when
        underTest.deleteAuthor(author);

        // then
        assertSoftly(softly -> {
            softly.assertThat(authorRepository.findById(author.getId())).isNotPresent();
            softly.assertThat(bookRepository.findById(book.getId())).isNotPresent();
        });
    }

    @Test
    public void deleteAuthor_shouldDeleteOnlyBookExclusiveForDeletedAuthor() {
        // given
        Book book = createBook("Exclusive relation with deleted author");
        Book bookWithMultipleAuthors = createBook("Multiple authors");

        Author authorToDelete = new Author("Joe Doe",
                new HashSet<>(Arrays.asList(book, bookWithMultipleAuthors)));
        Author author = new Author("Test Author",
                new HashSet<>(Collections.singletonList(bookWithMultipleAuthors)));
        authorRepository.save(authorToDelete);
        authorRepository.save(author);
        bookRepository.save(book);
        bookRepository.save(bookWithMultipleAuthors);

        // when
        underTest.deleteAuthor(authorToDelete);

        // then
        assertSoftly(softly -> {
            softly.assertThat(authorRepository.findById(authorToDelete.getId())).isNotPresent();
            softly.assertThat(authorRepository.findById(author.getId())).isPresent();
            softly.assertThat(bookRepository.findById(book.getId())).isNotPresent();
            softly.assertThat(bookRepository.findById(bookWithMultipleAuthors.getId())).isPresent();
        });
    }

    private Book createBook(String title) {
        Genre genre = new Genre(GenreName.SATIRE);
        genreRepository.save(genre);
        Lang lang = new Lang(LanguageName.ENGLISH);
        languageRepository.save(lang);
        PublishingFormat publishingFormat = new PublishingFormat(PublishingFormat.Format.HARDCOVER);
        publishingFormatRepository.save(publishingFormat);
        return new Book(
                title,
                lang,
                "Test Blurb",
                genre,
                publishingFormat
        );
    }
}
