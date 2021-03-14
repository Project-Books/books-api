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

package com.karankumar.booksapi.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("Book Series Mapping should")
public class BookSeriesMappingTest {

    @Test
    @DisplayName("throw a Null Pointer Exception on an attempt to create with Null Serial Number")
    void notAcceptNullSerialNumber(){

        BookSeries bookSeries = new BookSeries("Harry Potter Series");

        Author author = new Author("J.K.", "Rowling");
        author.setAbout("A fantastic author");

        Book book = new Book(
                "Harry Potter and the Philosopher's stone",
                new Author[] {author},
                Language.ENGLISH,
                "Sample blurb value",
                BookGenre.FANTASY,
                BookFormat.PAPERBACK
        );
        book.setYearOfPublication(1997);
        book.setIsbn13("9781408810545");
        book.setPublishedBy(Publisher.BLOOMSBURY);

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(()->new BookSeriesMapping(bookSeries, book, null));
    }

    @Test
    @DisplayName("throw a Null Pointer Exception on an attempt to create with Null Book")
    void notAcceptNullBook(){

        BookSeries bookSeries = new BookSeries("Harry Potter Series");

        Author author = new Author("J.K.", "Rowling");
        author.setAbout("A fantastic author");

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(()->new BookSeriesMapping(bookSeries, null, 1));
    }

    @Test
    @DisplayName("throw a Null Pointer Exception on an attempt to create with Null Book Series.")
    void notAcceptNullBookSeries(){

        Author author = new Author("J.K.", "Rowling");
        author.setAbout("A fantastic author");

        Book book = new Book(
                "Harry Potter and the Philosopher's stone",
                new Author[] {author},
                Language.ENGLISH,
                "Sample blurb value",
                BookGenre.FANTASY,
                BookFormat.PAPERBACK
        );
        book.setYearOfPublication(1997);
        book.setIsbn13("9781408810545");
        book.setPublishedBy(Publisher.BLOOMSBURY);

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(()->new BookSeriesMapping(null, book, 1));
    }
}
