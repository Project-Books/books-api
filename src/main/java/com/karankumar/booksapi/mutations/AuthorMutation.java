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
import com.karankumar.booksapi.model.Author;
import com.karankumar.booksapi.service.AuthorService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;

@DgsComponent
public class AuthorMutation {
    private final AuthorService authorService;
    public static final String NOT_FOUND_ERROR_MESSAGE = "Author not found";

    public AuthorMutation(AuthorService authorService) {
        this.authorService = authorService;
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.AddAuthor)
    public Author addAuthor(DataFetchingEnvironment dataFetchingEnvironment) {
        String fullName = dataFetchingEnvironment.getArgument(DgsConstants.AUTHOR.FullName);
        return authorService.save(new Author(fullName, new HashSet<>()));
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.DeleteAuthor)
    @Transactional
    public Author deleteAuthor(DataFetchingEnvironment dataFetchingEnvironment) {
        String stringId = dataFetchingEnvironment.getArgument(DgsConstants.AUTHOR.Id);
        if (stringId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_ERROR_MESSAGE);
        }
        Long id = Long.parseLong(stringId);

        Optional<Author> author = authorService.findById(id);
        if (author.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_ERROR_MESSAGE);
        }

        authorService.deleteAuthor(author.get());

        return author.get();
    }
}
