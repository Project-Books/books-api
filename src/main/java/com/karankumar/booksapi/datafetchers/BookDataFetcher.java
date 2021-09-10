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

package com.karankumar.booksapi.datafetchers;

import com.karankumar.booksapi.DgsConstants;
import com.karankumar.booksapi.model.Book;
import com.karankumar.booksapi.service.BookService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;

import java.util.List;

@DgsComponent
public class BookDataFetcher {
    private final BookService bookService;

    public BookDataFetcher(BookService bookService) {
        this.bookService = bookService;
    }

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Book)
    public Book findBookByFilter(@InputArgument String isbn13, @InputArgument String title) {
        // TODO: handle filter
        return bookService.findBookByIsbn13(isbn13);
    }

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.AuthorBooks)
    public List<Book> findByAuthor(@InputArgument(DgsConstants.AUTHOR.FullName) String fullName) {
        return bookService.findByAuthor(fullName);
    }
    
    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.FindByPublisher)
    public List<Book> findByPublisher(@InputArgument(DgsConstants.PUBLISHER.Name) String publisherName) {
        return bookService.findByPublisher(publisherName);
    }
}
