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
import com.karankumar.booksapi.model.BookFormat;
import com.karankumar.booksapi.model.BookGenre;
import com.karankumar.booksapi.model.BookSeries;
import com.karankumar.booksapi.model.BookSeriesMapping;
import com.karankumar.booksapi.model.Language;
import com.karankumar.booksapi.model.PublisherName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaIntegrationTest
@DisplayName("BookSeriesMappingRepository should")
class BookSeriesMappingRepositoryTest {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookSeriesRepository bookSeriesRepository;
    private final BookSeriesMappingRepository bookSeriesMappingRepository;

    @Autowired
    BookSeriesMappingRepositoryTest(BookRepository bookRepository,
                                    AuthorRepository authorRepository,
                                    BookSeriesRepository bookSeriesRepository,
                                    BookSeriesMappingRepository bookSeriesMappingRepository) {

        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookSeriesRepository = bookSeriesRepository;
        this.bookSeriesMappingRepository = bookSeriesMappingRepository;
    }

    @BeforeEach
    void setUp() {
        bookSeriesMappingRepository.deleteAll();
        authorRepository.deleteAll();
        bookRepository.deleteAll();
        bookSeriesRepository.deleteAll();
    }

    @Test
    @DisplayName("return all books in a series.")
    void fetchAllBooksInBookSeries() {
        BookSeries bs = createBookSeriesMapping();
        List<Book> assertion = bookSeriesMappingRepository.getAllBooksByBookSeries(bs);

        List<Book> bookList = new ArrayList<>();
        bookList.add(bookRepository.findBookByIsbn13("9781408810545"));
        bookList.add(bookRepository.findBookByIsbn13("1234567898765"));

        assertThat(assertion).hasSize(bookList.size()).hasSameElementsAs(bookList);
    }

    private BookSeries createBookSeriesMapping() {
        Author author = new Author("J.K. Rowling");
        author.setAbout("A fantastic author");
        authorRepository.save(author);

        Book book1 = createBook(author, "Harry Potter and the Philosopher's stone");
        bookRepository.save(book1);

        Book book2 = createBook(author, "Harry Potter and the Chamber of Secrets");
        bookRepository.save(book2);

        BookSeries bookSeries = new BookSeries("Harry Potter Series");
        bookSeriesRepository.save(bookSeries);

        bookSeriesMappingRepository.save(new BookSeriesMapping(bookSeries, book1, 1));
        bookSeriesMappingRepository.save(new BookSeriesMapping(bookSeries, book2, 2));

        return bookSeries;
    }

    private Book createBook(Author author, String title) {
        return new Book(
                title, new Author[]{author},
                Language.ENGLISH, "Sample blurb value",
                BookGenre.FANTASY, BookFormat.PAPERBACK
        );
    }
}
