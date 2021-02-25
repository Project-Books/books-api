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

package com.karankumar.booksapi.resolver;

import com.karankumar.booksapi.model.Author;
import com.karankumar.booksapi.model.Book;
import com.karankumar.booksapi.model.BookFormat;
import com.karankumar.booksapi.model.BookGenre;
import com.karankumar.booksapi.model.Language;
import com.karankumar.booksapi.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Query resolver should")
class QueryResolverTests {
    @Test
    void findByExistingAuthor() {
        // given
        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        List<Book> books = new ArrayList<>();
        String firstName = "First";
        String lastName = "Last";
        Author author = new Author(firstName, lastName);
        books.add(new Book("title", new Author[] {author}, Language.ENGLISH, "blurb",
                BookGenre.HISTORY, BookFormat.HARDCOVER));

        // when
        Mockito.when(bookRepository.findByAuthor(firstName, lastName)).thenReturn(books);
        List<Book> actual = bookRepository.findByAuthor(firstName, lastName);

        // then
        assertThat(actual).containsAll(books);
    }
}
