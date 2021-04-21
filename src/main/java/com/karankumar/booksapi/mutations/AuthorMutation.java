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

import java.util.HashSet;

@DgsComponent
public class AuthorMutation {
    private final AuthorService authorService;

    public AuthorMutation(AuthorService authorService) {
        this.authorService = authorService;
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.AddAuthor)
    public Author addAuthor(DataFetchingEnvironment dataFetchingEnvironment) {
        String fullName = dataFetchingEnvironment.getArgument(DgsConstants.AUTHOR.FullName);
        return authorService.save(new Author(fullName, new HashSet<>()));
    }
}
