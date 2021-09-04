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
import com.karankumar.booksapi.model.BookFormat;
import com.karankumar.booksapi.model.BookGenre;
import com.karankumar.booksapi.model.Language;
import com.karankumar.booksapi.repository.AuthorRepository;
import com.karankumar.booksapi.repository.BookRepository;
import com.karankumar.booksapi.repository.NativeQueryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@DataJpaIntegrationTest
@Import({NativeQueryRepository.class, AuthorService.class})
public class AuthorServiceIntegrationTest {

    private AuthorService underTest;
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private NativeQueryRepository nativeQueryRepository;

    @Autowired
    public AuthorServiceIntegrationTest(AuthorService underTest, AuthorRepository authorRepository, BookRepository bookRepository, NativeQueryRepository nativeQueryRepository) {
        this.underTest = underTest;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.nativeQueryRepository = nativeQueryRepository;
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
        Author author = new Author("Joe Doe", new HashSet<>(Collections.singletonList(book)));
        authorRepository.save(author);
        bookRepository.save(book);

        // when
        underTest.deleteAuthor(author);

        //then
        Assertions.assertThat(authorRepository.findById(author.getId())).isNotPresent();
        Assertions.assertThat(bookRepository.findById(book.getId())).isNotPresent();
    }

    @Test
    public void deleteAuthor_shouldDeleteOnlyBookExclusiveForDeletedAuthor() {
        // given
        Book book = createBook("Exclusive relation with deleted author");
        Book bookWithMultipleAuthors = createBook("Multiple authors");

        Author authorToDelete = new Author("Joe Doe", new HashSet<>(Arrays.asList(book, bookWithMultipleAuthors)));
        Author author = new Author("Test Author", new HashSet<>(Collections.singletonList(bookWithMultipleAuthors)));
        authorRepository.save(authorToDelete);
        authorRepository.save(author);
        bookRepository.save(book);
        bookRepository.save(bookWithMultipleAuthors);

        // when
        underTest.deleteAuthor(authorToDelete);

        //then
        Assertions.assertThat(authorRepository.findById(authorToDelete.getId())).isNotPresent();
        Assertions.assertThat(authorRepository.findById(author.getId())).isPresent();
        Assertions.assertThat(bookRepository.findById(book.getId())).isNotPresent();
        Assertions.assertThat(bookRepository.findById(bookWithMultipleAuthors.getId())).isPresent();
    }

    private Book createBook(String title) {
        Book book = new Book(
                title,
                Language.ENGLISH,
                "Test Blurb",
                BookGenre.SATIRE,
                BookFormat.HARDCOVER
        );

        return book;
    }
}
