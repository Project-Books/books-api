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

package com.karankumar.booksapi.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("Book should")
class BookTest {
    @Test
    @DisplayName("throw a Null Pointer Exception on an attempt to create with a null title")
    void notAcceptNullTitle() {
        // given
        Author author = new Author("J.K. Rowling");

        // when and then
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Book(
                        null,
                        new Author[] { author },
                        Language.ENGLISH,
                        "Sample blurb value", BookGenre.FANTASY,
                        BookFormat.HARDCOVER
                ));
    }

    @Test
    @DisplayName("throw a Null Pointer Exception on an attempt to create with a null Author")
    void notAcceptNullAuthor() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Book(
                        "The Hobbit", null, Language.ARABIC, "Sample blurb value", BookGenre.FANTASY,
                        BookFormat.EBOOK
                ));
    }

    @Test
    @DisplayName("throw a Null Pointer Exception on an attempt to create with a null language")
    void notAcceptNullLanguage() {
        // given
        Author author = new Author("J.K. Rowling");

        // when and then
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Book(
                        "The Hobbit",
                        new Author[] {author},
                        null,
                        "Sample blurb value",
                        BookGenre.FANTASY,
                        BookFormat.HARDCOVER
                ));
    }
}
