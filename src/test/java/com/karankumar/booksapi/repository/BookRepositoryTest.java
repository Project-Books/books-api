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
import com.karankumar.booksapi.model.enums.BookGenre;
import com.karankumar.booksapi.model.enums.LanguageName;
import com.karankumar.booksapi.model.format.Format;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;

import static com.karankumar.booksapi.repository.RepositoryTestUtils.createBook;
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
    
    @Autowired
    BookRepositoryTest(BookRepository bookRepository, AuthorRepository authorRepository,
                       PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    @BeforeEach
    void setUp() {
        authorRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    void findSavedBooks() {
        // given
        Book book = createBook();
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
        Book book = new Book(
                "Game of APIs",
                LanguageName.ENGLISH,
                "",
                BookGenre.SATIRE,
                new Format()
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
        Book book = new Book(
                TITLE,
                LanguageName.ENGLISH,
                "",
                BookGenre.ART,
                new Format()
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
        Book book = new Book(
                TITLE,
                LanguageName.ENGLISH,
                "",
                BookGenre.MYSTERY,
                new Format()
        );
        bookRepository.save(book);

        // when
        Book result = bookRepository.findByTitleIgnoreCase(TITLE.toLowerCase());

        // then
        assertThat(result).isEqualTo(book);
    }
}
