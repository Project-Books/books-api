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

package com.karankumar.booksapi.service;

import com.karankumar.booksapi.model.Author;
import com.karankumar.booksapi.model.Book;
import com.karankumar.booksapi.repository.AuthorRepository;
import com.karankumar.booksapi.repository.BookRepository;
import com.karankumar.booksapi.repository.NativeQueryRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final NativeQueryRepository nativeQueryRepository;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository, NativeQueryRepository nativeQueryRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.nativeQueryRepository = nativeQueryRepository;
    }

    public Author save(@NonNull Author author) {
        return authorRepository.save(author);
    }

    public Optional<Author> findById(@NonNull Long id) {
        return authorRepository.findById(id);
    }

    public List<Author> findAll() {
        return authorRepository.findAllAuthors();
    }

    public void deleteAuthor(@NonNull Author author) {
        Set<Book> booksWithOneAuthor = nativeQueryRepository.getAllBooksAuthorWroteAlone(author.getId());

        nativeQueryRepository.deleteAllAuthorAssociations(author.getId());
        bookRepository.deleteAll(booksWithOneAuthor);

        authorRepository.deleteById(author.getId());
    }

}
