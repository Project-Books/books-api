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
import com.acme.client.*;
import com.acme.types.AwardName;
import com.karankumar.booksapi.datafetchers.BookDataFetcher;
import com.karankumar.booksapi.model.Book;
import com.karankumar.booksapi.model.Publisher;
import com.karankumar.booksapi.model.PublishingFormat;
import com.karankumar.booksapi.model.genre.Genre;
import com.karankumar.booksapi.model.genre.GenreName;
import com.karankumar.booksapi.model.language.Lang;
import com.karankumar.booksapi.model.language.LanguageName;
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
                title,
                new Lang(LanguageName.ENGLISH),
                "blurb",
                new Genre(GenreName.CRIME),
                new PublishingFormat()
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
    void findAllBooks_returnsNonEmptyListWithPublisher_whenBooksExist() {
        // Given
        final String publisherName = "HarperCollins";
        Publisher publisher = new Publisher(publisherName);
        Book book = new Book(
                "Abhorsen", new Lang(LanguageName.ENGLISH), "blurb",
                new Genre(GenreName.FANTASY),
                new PublishingFormat(PublishingFormat.Format.PAPERBACK)
        );
        publisher.addBook(book);
        given(bookService.findAll()).willReturn(List.of(book));
        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                new FindAllBooksGraphQLQuery(),
                new FindAllBooksProjectionRoot().publishers().name()
        );

        // When
        List<String> publishers = queryExecutor.executeAndExtractJsonPath(
                graphQLQueryRequest.serialize(),
                ROOT + DgsConstants.QUERY.FindAllBooks + "[*]." +
                        DgsConstants.BOOK.Publishers + "[*]." + DgsConstants.PUBLISHER.Name
        );

        // Then
        assertThat(publishers).containsExactly(publisherName);
    }

    @Test
    void findBookByIsbn13_returnsNonNullBook_whenIsbnMatchesABook() {
        // Given
        final String isbn13 = "1234567898765";
        Book book = new Book(
                "title",
                new Lang(LanguageName.ENGLISH),
                "blurb",
                new Genre(GenreName.CRIME),
                new PublishingFormat()
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
                "title",
                new Lang(LanguageName.ENGLISH), "blurb", new Genre(GenreName.CRIME),
                new PublishingFormat()
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
                title, new Lang(LanguageName.ENGLISH), "blurb", new Genre(GenreName.CRIME),
                new PublishingFormat()
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

    @Test
    void findByGenre_returnsNonNullBook_whenGenreFound() {
        // Given
        GenreName genreName = GenreName.MYSTERY;
        Book book = new Book(
                "April in Spain", new Lang(LanguageName.SPANISH), "blurb", new Genre(genreName),
                new PublishingFormat()
        );
        given(bookService.findByGenre(genreName)).willReturn(List.of(book));
        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                FindByGenreGraphQLQuery.newRequest()
                        .name(com.acme.types.GenreName.valueOf(genreName.name()))
                        .build(),
                new FindByGenreProjectionRoot().genre().name()
        );

        // When
        List<String> actual = queryExecutor.executeAndExtractJsonPath(
                graphQLQueryRequest.serialize(),
                ROOT + DgsConstants.QUERY.FindByGenre + "[*]." + DgsConstants.BOOK.Genre + ".name"
        );

        // Then
        assertThat(actual).containsExactly(genreName.getGenre());
    }

    @Test
    void findByAward_returnsEmptyList_whenBooksNotFound() {
        // Given
        given(bookService.findByAward(any(String.class))).willReturn(new ArrayList<>());
        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                FindByAwardNameGraphQLQuery.newRequest()
                        .awardName(AwardName.ORWELL_PRIZE)
                        .build(),
                new FindByAwardNameProjectionRoot().title()
        );

        // When
        List<String> actual = queryExecutor.executeAndExtractJsonPath(
                graphQLQueryRequest.serialize(),
                ROOT + DgsConstants.QUERY.FindByAwardName + "[*]." + DgsConstants.AWARD.AwardName
        );

        // Then
        assertThat(actual).isEmpty();
    }
}
