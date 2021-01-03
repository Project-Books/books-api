/*
 * Copyright (C) 2020  Karan Kumar
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

import com.karankumar.booksapi.model.Author;
import com.karankumar.booksapi.model.Book;
import com.karankumar.booksapi.model.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static com.karankumar.booksapi.repository.RepositoryTestUtils.createBook;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@DisplayName("BookRepository should")
class BookRepositoryTest {
    private static final String ISBN = "978-3-16-148410-0";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    
    private Author author1;
    private Author author2;

    @Autowired
    BookRepositoryTest(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }
    @BeforeEach
    void setUp() {
        authorRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    @DisplayName("find saved books")
    void findSavedBooks() {
        // given
        createAndSaveAuthors();
        Book book = createBook(author1, author2);
        bookRepository.save(book);

        // when
        List<Book> result = bookRepository.findAllBooks();

        // then
        assertSoftly(softly -> {
            softly.assertThat(result.size()).isOne();
            softly.assertThat(result).containsExactlyInAnyOrder(book);
        });
    }

    @Test
    @DisplayName("find book with isbn")
    void findBookByIsbn() {
        // given
        createAndSaveAuthors();
        Book book = createBookwithISBN();
        bookRepository.save(book);

        // when
        Book result = bookRepository.findBookByIsbn13(ISBN);

        // then
        assertThat(result).isEqualTo(book);
    }
  
    private void createAndSaveAuthors() {
        author1 = new Author("Kevlin", "Henney");
        author2 = new Author("Trisha", "Gee");
        saveAllAuthors(author1, author2);
    }

    private Book createBookwithISBN() {
        Book book = new Book(
                "Game of APIs",
                new Author[]{author1, author2},
                Language.ENGLISH,
                ""
        );
        book.setIsbn13(ISBN);
        return book;
    }

    private void saveAllAuthors(Author... authors) {
        Arrays.stream(authors).forEach(authorRepository::save);
    }
}
