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

package com.karankumar.booksapi.datafetcher;

import com.karankumar.booksapi.DgsConstants;
import com.karankumar.booksapi.client.AuthorBooksGraphQLQuery;
import com.karankumar.booksapi.client.AuthorBooksProjectionRoot;
import com.karankumar.booksapi.client.BookGraphQLQuery;
import com.karankumar.booksapi.client.BookProjectionRoot;
import com.karankumar.booksapi.datafetchers.BookDataFetcher;
import com.karankumar.booksapi.model.Book;
import com.karankumar.booksapi.model.BookFormat;
import com.karankumar.booksapi.model.BookGenre;
import com.karankumar.booksapi.model.Language;
import com.karankumar.booksapi.service.BookService;
import com.karankumar.booksapi.types.BookFilter;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = {DgsAutoConfiguration.class, BookDataFetcher.class})
@ActiveProfiles("test")
class BookDataFetcherTest {
    @Autowired
    DgsQueryExecutor queryExecutor;

    @MockBean
    BookService bookService;

    private final String ROOT = "data.";

    @Test
    void findBookByIsbn13_returnsNonNullBook_whenIsbnMatchesABook() {
        // Given
        final String isbn13 = "1234567898765";
        Book book = new Book(
                "title", Language.ENGLISH, "blurb", BookGenre.CRIME, BookFormat.PAPERBACK
        );
        book.setIsbn13(isbn13);
        given(bookService.findBookByIsbn13(isbn13)).willReturn(book);
        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                BookGraphQLQuery.newRequest()
                                .filter(new BookFilter(null, isbn13))
                                .build(),
                new BookProjectionRoot().isbn13()
        );

        // When
        String actual = queryExecutor.executeAndExtractJsonPath(
                graphQLQueryRequest.serialize(),
                ROOT + DgsConstants.QUERY.Book + "." + DgsConstants.BOOK.Isbn13
        );

        // Then
        assertThat(actual).isEqualTo(isbn13);
    }

    @Test
    void findBookByIsbn13_returnsNullBook_whenIsbnDoesNotMatchABook() {
        // Given
        final String isbn13 = "1234567898765";
        given(bookService.findBookByIsbn13(isbn13)).willReturn(null);
        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                BookGraphQLQuery.newRequest()
                                .filter(new BookFilter(null, isbn13))
                                .build(),
                new BookProjectionRoot().isbn13()
        );

        // When
        String actual = queryExecutor.executeAndExtractJsonPath(
                graphQLQueryRequest.serialize(),
                ROOT + DgsConstants.QUERY.Book
        );

        // Then
        assertThat(actual).isNull();
    }

    @Test
    void findByAuthor_returnsNonEmptyList_whenBookNotFound() {
        // Given
        Book book = new Book(
                "title", Language.ENGLISH, "blurb", BookGenre.CRIME, BookFormat.PAPERBACK
        );
        given(bookService.findByAuthor(any(String.class))).willReturn(List.of(book));
        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                AuthorBooksGraphQLQuery.newRequest()
                                       .fullName("name")
                                       .build(),
                new AuthorBooksProjectionRoot().title()
        );

        // When
        List<String> actual = queryExecutor.executeAndExtractJsonPath(
                graphQLQueryRequest.serialize(),
                ROOT + DgsConstants.QUERY.AuthorBooks + "[*]." + DgsConstants.BOOK.Title
        );

        // Then
        assertThat(actual).isNotEmpty();
    }

    @Test
    void findByTitle_returnsNullBook_whenTitleNotFound() {
        // Given
        String title = "title";
        given(bookService.findByTitle(title)).willReturn(null);
        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                BookGraphQLQuery.newRequest()
                                .filter(new BookFilter(title, null))
                                .build(),
                new BookProjectionRoot().title()
        );

        // When
        String actual = queryExecutor.executeAndExtractJsonPath(
                graphQLQueryRequest.serialize(),
                ROOT + DgsConstants.QUERY.Book
        );

        // Then
        assertThat(actual).isNull();
    }

    @Test
    void findByTitle_returnsNonNullBook_whenTitleFound() {
        // Given
        String title = "title";
        Book book = new Book(
                title, Language.ENGLISH, "blurb", BookGenre.CRIME, BookFormat.PAPERBACK
        );
        given(bookService.findByTitle(title)).willReturn(book);
        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                BookGraphQLQuery.newRequest()
                                .filter(new BookFilter(title, null))
                                .build(),
                new BookProjectionRoot().title()
        );

        // When
        String actual = queryExecutor.executeAndExtractJsonPath(
                graphQLQueryRequest.serialize(),
                ROOT + DgsConstants.QUERY.Book + ".title"
        );

        // Then
        assertThat(actual).isNotNull();
    }
}
