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

import com.acme.DgsConstants;
import com.karankumar.booksapi.model.Author;
import com.karankumar.booksapi.model.Book;
import com.karankumar.booksapi.model.PublishingFormat;
import com.karankumar.booksapi.model.genre.Genre;
import com.karankumar.booksapi.model.genre.GenreName;
import com.karankumar.booksapi.model.language.Lang;
import com.karankumar.booksapi.model.language.LanguageName;
import com.karankumar.booksapi.service.AuthorService;
import graphql.schema.DataFetchingEnvironment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static com.karankumar.booksapi.mutations.AuthorMutation.NOT_FOUND_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class AuthorMutationTest {

    @MockBean
    private AuthorService authorService;

    @Autowired
    private AuthorMutation underTest;

    @MockBean
    private DataFetchingEnvironment dataFetchingEnvironment;

    @Test
    public void deleteAuthor_throws_whenIdWasNotFound() {
        // given
        when(dataFetchingEnvironment.getArgument(DgsConstants.AUTHOR.Id)).thenReturn(null);

        // when/then
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> underTest.deleteAuthor(dataFetchingEnvironment));

        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(exception.getReason()).isEqualTo(NOT_FOUND_ERROR_MESSAGE);
    }

    @Test
    public void deleteAuthor_throws_whenAuthorWasNotFound() {
        // given
        when(dataFetchingEnvironment.getArgument(DgsConstants.AUTHOR.Id)).thenReturn("1");
        when(authorService.findById(1L)).thenReturn(Optional.empty());

        // when/then
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> underTest.deleteAuthor(dataFetchingEnvironment));

        verify(authorService, times(1)).findById(1L);

        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(exception.getReason()).isEqualTo(NOT_FOUND_ERROR_MESSAGE);
    }

    @Test
    public void deleteAuthor_shouldDeletedReturnAuthor_whenAuthorWasDeleted() {
        // given
        Book book = new Book("TestTitle", new Lang(LanguageName.AFRIKAANS), "Blurb", new Genre(GenreName.ADVENTURE), new PublishingFormat(PublishingFormat.Format.HARDCOVER));
        Author author = new Author("Test Author", new HashSet<>(Collections.singletonList(book)));

        when(dataFetchingEnvironment.getArgument(DgsConstants.AUTHOR.Id)).thenReturn("1");
        when(authorService.findById(1L)).thenReturn(Optional.of(author));

        // when
        Author resultAuthor = underTest.deleteAuthor(dataFetchingEnvironment);

        // then
        verify(authorService, times(1)).deleteAuthor(author);

        assertThat(resultAuthor).isSameAs(author);
    }

}