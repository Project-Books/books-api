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

import com.karankumar.booksapi.client.FindAllBooksGraphQLQuery;
import com.karankumar.booksapi.client.FindAllBooksProjectionRoot;
import com.karankumar.booksapi.datafetchers.BookDataFetcher;
import com.karankumar.booksapi.model.Book;
import com.karankumar.booksapi.model.BookFormat;
import com.karankumar.booksapi.model.BookGenre;
import com.karankumar.booksapi.model.Language;
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
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = {DgsAutoConfiguration.class, BookDataFetcher.class})
@ActiveProfiles("test")
class BookDataFetcherTest {
    @Autowired
    DgsQueryExecutor queryExecutor;

    @MockBean
    BookService bookService;

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
                "data.findAllBooks[*].title"
        );

        // Then
        assertThat(bookTitles.size()).isZero();
    }

    @Test
    void findAllBooks_returnsNonEmptyList_whenBooksExist() {
        // Given
        final String title = "How to avoid a climate disaster";
        given(bookService.findAll()).willReturn(List.of(createBook(title)));
        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                new FindAllBooksGraphQLQuery(),
                new FindAllBooksProjectionRoot().title()
        );

        // When
        List<String> bookTitles = queryExecutor.executeAndExtractJsonPath(
                graphQLQueryRequest.serialize(),
                "data.findAllBooks[*].title"
        );

        // Then
        assertThat(bookTitles).containsExactly(title);
    }

    private Book createBook(String title) {
        return new Book(
                title, Language.ENGLISH, "blurb", BookGenre.CRIME, BookFormat.PAPERBACK
        );
    }
}
