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

import com.karankumar.booksapi.model.Book;
import com.karankumar.booksapi.model.PublishingFormat;
import com.karankumar.booksapi.model.genre.Genre;
import com.karankumar.booksapi.model.genre.GenreName;
import com.karankumar.booksapi.model.language.Lang;
import com.karankumar.booksapi.model.language.LanguageName;

final class RepositoryTestUtils {
    private RepositoryTestUtils() { }

    static Book createBook() {
        Book book = new Book(
                "97 Things Every Java Programmer Should Know",
                new Lang(LanguageName.ENGLISH),
                "Sample blurb value",
                new Genre(GenreName.CHILDREN),
                new PublishingFormat()
        );
        book.setYearOfPublication(2019);
        book.setIsbn13("9781408670545");
        return book;
    }

}
