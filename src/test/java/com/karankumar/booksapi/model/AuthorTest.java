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

@DisplayName("Author should")
class AuthorTest {
    @Test
    @DisplayName("throw a Null Pointer Exception on an attempt to create with a null first name")
    void notAcceptNullFirstName() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Author(null, "Rowling"));
    }

    @Test
    @DisplayName("throw a Null Pointer Exception on an attempt to create with a null last name")
    void notAcceptNullLastName() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Author("J.K.", null));
    }
}
