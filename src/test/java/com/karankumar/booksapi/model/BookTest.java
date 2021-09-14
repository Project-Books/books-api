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

import com.karankumar.booksapi.model.genre.GenreName;
import com.karankumar.booksapi.model.language.Language;
import com.karankumar.booksapi.model.language.LanguageName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("Book should")
class BookTest {
    @Test
    @DisplayName("throw a Null Pointer Exception on an attempt to create with a null title")
    void notAcceptNullTitle() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Book(
                        null,
                        new Language(LanguageName.ENGLISH),
                        "Sample blurb value", GenreName.FANTASY,
                        new PublishingFormat()
                ));
    }

    @Test
    @DisplayName("throw a Null Pointer Exception on an attempt to create with a null language")
    void notAcceptNullLanguage() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Book(
                        "The Hobbit",
                        null,
                        "Sample blurb value",
                        GenreName.FANTASY,
                        new PublishingFormat()
                ));
    }
}
