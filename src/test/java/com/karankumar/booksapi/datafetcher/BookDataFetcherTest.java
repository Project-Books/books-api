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

import com.acme.DgsConstants;
import com.acme.client.FindAllBooksGraphQLQuery;
import com.acme.client.FindAllBooksProjectionRoot;
import com.acme.client.FindBookByIsbn13GraphQLQuery;
import com.acme.client.FindBookByIsbn13ProjectionRoot;
import com.acme.client.FindByAuthorGraphQLQuery;
import com.acme.client.FindByAuthorProjectionRoot;
import com.acme.client.FindByTitleIgnoreCaseGraphQLQuery;
import com.acme.client.FindByTitleIgnoreCaseProjectionRoot;
import com.karankumar.booksapi.datafetchers.BookDataFetcher;
import com.karankumar.booksapi.model.Book;
import com.karankumar.booksapi.model.enums.BookGenre;
import com.karankumar.booksapi.model.enums.LanguageName;
import com.karankumar.booksapi.model.format.Format;
import com.karankumar.booksapi.service.BookService;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
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
    void findAllBooks_returnsEmptyList_whenNoBooksExist() {
        // Given
        given(bookService.findAll()).willReturn(new ArrayList<>());
        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                new FindAllBooksGraphQLQuery(),
                new FindAllBooksProjectionRoot().title()
        );

        // When
        List<String> bookTitles = queryExecutor.executeAndExtractJsonPath(
                graphQLQueryRequest.serialize(),
                ROOT + DgsConstants.QUERY.FindAllBooks + "[*]." + DgsConstants.BOOK.Title
        );

        // Then
        assertThat(bookTitles.size()).isZero();
    }

    @Test
    void findAllBooks_returnsNonEmptyList_whenBooksExist() {
        // Given
        final String title = "How to avoid a climate disaster";
        Book book = new Book(
                title, LanguageName.ENGLISH, "blurb", BookGenre.CRIME, new Format()
        );
        given(bookService.findAll()).willReturn(List.of(book));
        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                new FindAllBooksGraphQLQuery(),
                new FindAllBooksProjectionRoot().title()
        );

        // When
        List<String> bookTitles = queryExecutor.executeAndExtractJsonPath(
                graphQLQueryRequest.serialize(),
                ROOT + DgsConstants.QUERY.FindAllBooks + "[*]." + DgsConstants.BOOK.Title
        );

        // Then
        assertThat(bookTitles).containsExactly(title);
    }

    @Test
    void findBookByIsbn13_returnsNonNullBook_whenIsbnMatchesABook() {
        // Given
        final String isbn13 = "1234567898765";
        Book book = new Book(
                "title", LanguageName.ENGLISH, "blurb", BookGenre.CRIME, new Format()
        );
        book.setIsbn13(isbn13);
        given(bookService.findBookByIsbn13(isbn13)).willReturn(book);
        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                FindBookByIsbn13GraphQLQuery.newRequest().isbn13(isbn13).build(),
                new FindBookByIsbn13ProjectionRoot().isbn13()
        );

        // When
        String actual = queryExecutor.executeAndExtractJsonPath(
                graphQLQueryRequest.serialize(),
                ROOT + DgsConstants.QUERY.FindBookByIsbn13 + "." + DgsConstants.BOOK.Isbn13
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
                FindBookByIsbn13GraphQLQuery.newRequest()
                                            .isbn13(isbn13)
                                            .build(),
                new FindBookByIsbn13ProjectionRoot().isbn13()
        );

        // When
        String actual = queryExecutor.executeAndExtractJsonPath(
                graphQLQueryRequest.serialize(),
                ROOT + DgsConstants.QUERY.FindBookByIsbn13
        );

        // Then
        assertThat(actual).isNull();
    }

    @Test
    void findByAuthor_returnsNonEmptyList_whenBookNotFound() {
        // Given
        Book book = new Book(
                "title", LanguageName.ENGLISH, "blurb", BookGenre.CRIME, new Format()
        );
        given(bookService.findByAuthor(any(String.class))).willReturn(List.of(book));
        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                FindByAuthorGraphQLQuery.newRequest()
                                        .fullName("name")
                                        .build(),
                new FindByAuthorProjectionRoot().title()
        );

        // When
        List<String> actual = queryExecutor.executeAndExtractJsonPath(
                graphQLQueryRequest.serialize(),
                ROOT + DgsConstants.QUERY.FindByAuthor + "[*]." + DgsConstants.BOOK.Title
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
                FindByTitleIgnoreCaseGraphQLQuery.newRequest()
                                                 .title(title)
                                                 .build(),
                new FindByTitleIgnoreCaseProjectionRoot().title()
        );

        // When
        String actual = queryExecutor.executeAndExtractJsonPath(
                graphQLQueryRequest.serialize(),
                ROOT + DgsConstants.QUERY.FindByTitleIgnoreCase
        );

        // Then
        assertThat(actual).isNull();
    }

    @Test
    void findByTitle_returnsNonNullBook_whenTitleFound() {
        // Given
        String title = "title";
        Book book = new Book(
                title, LanguageName.ENGLISH, "blurb", BookGenre.CRIME, new Format()
        );
        given(bookService.findByTitle(title)).willReturn(book);
        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                FindByTitleIgnoreCaseGraphQLQuery.newRequest()
                                                 .title(title)
                                                 .build(),
                new FindByTitleIgnoreCaseProjectionRoot().title()
        );

        // When
        String actual = queryExecutor.executeAndExtractJsonPath(
                graphQLQueryRequest.serialize(),
                ROOT + DgsConstants.QUERY.FindByTitleIgnoreCase + ".title"
        );

        // Then
        assertThat(actual).isNotNull();
    }
}
