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
import com.karankumar.booksapi.model.BookFormat;
import com.karankumar.booksapi.model.BookGenre;
import com.karankumar.booksapi.model.Language;
import com.karankumar.booksapi.model.Publisher;

final class RepositoryTestUtils {
    private RepositoryTestUtils() { }

    static Book createBook(Author... authors) {
        Book book = new Book(
                "97 Things Every Java Programmer Should Know",
                authors,
                Language.ENGLISH
        );
        book.setGenre(BookGenre.REFERENCE);
        book.setYearOfPublication(2019);
        book.setIsbn13("9781408670545");
        book.setPublishedBy(Publisher.CAMBRIDGE_UNIVERSITY_PRESS);
        book.setFormat(BookFormat.PAPERBACK);
        return book;
    }

}
