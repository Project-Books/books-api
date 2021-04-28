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

package com.karankumar.booksapi.mutations;

import com.karankumar.booksapi.DgsConstants;
import com.karankumar.booksapi.exception.InvalidISBN10Exception;
import com.karankumar.booksapi.exception.InvalidISBN13Exception;
import com.karankumar.booksapi.model.Book;
import com.karankumar.booksapi.service.BookService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@DgsComponent
public class BookMutation {
    private final BookService bookService;
    private static final String NOT_FOUND_ERROR_MESSAGE = "Book not found";

    public BookMutation(BookService bookService) {
        this.bookService = bookService;
    }

    // TODO: change this to update a book rather than updating a specific field
    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.AddIsbn13)
    public Book addIsbn13(DataFetchingEnvironment dataFetchingEnvironment)
            throws InvalidISBN10Exception, InvalidISBN13Exception {
        Optional<Book> optionalBook = bookService.findById(
                dataFetchingEnvironment.getArgument(DgsConstants.BOOK.Id)
        );

        if (optionalBook.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_ERROR_MESSAGE);
        }

        String isbn13 = dataFetchingEnvironment.getArgument(DgsConstants.BOOK.Isbn13);
        Book book = optionalBook.get();
        book.setIsbn13(isbn13);
        return bookService.save(optionalBook.get());
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.DeleteBook)
    public Book deleteBook(DataFetchingEnvironment dataFetchingEnvironment) {
        String stringId = dataFetchingEnvironment.getArgument(DgsConstants.BOOK.Id);
        if (stringId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_ERROR_MESSAGE);
        }
        Long id = Long.parseLong(stringId);

        Optional<Book> book = bookService.findById(id);
        if (book.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_ERROR_MESSAGE);
        }

        bookService.deleteBook(id);
        return book.get();
    }
}
