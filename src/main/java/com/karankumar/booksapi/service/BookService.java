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

import com.karankumar.booksapi.exception.InvalidISBN13Exception;
import com.karankumar.booksapi.model.Book;
import com.karankumar.booksapi.repository.BookRepository;
import lombok.NonNull;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(@NonNull Book book) throws InvalidISBN13Exception {
        ISBNValidator isbnValidator = new ISBNValidator();
        if (!isbnValidator.isValidISBN13(book.getIsbn13())) {
            throw new InvalidISBN13Exception("Not a valid ISBN 13: " + book.getIsbn13());
        }
        return bookRepository.save(book);
    }

    public Optional<Book> findById(@NonNull Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> findAll() {
        return bookRepository.findAllBooks();
    }

    public List<Book> findByAuthor(@NonNull String firstName, @NonNull String lastName) {
        return bookRepository.findByAuthor(firstName, lastName);
    }

    public Book findBookByIsbn13(@NonNull String isbn13) {
        return bookRepository.findBookByIsbn13(isbn13);
    }

    /**
     * Case-insensitive search
     * @param title to search by
     * @return a matching Book or null otherwise
     */
    public Book findByTitle(@NonNull String title) {
        return bookRepository.findByTitleIgnoreCase(title);
    }
}
