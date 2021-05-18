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

package com.karankumar.booksapi;

import com.karankumar.booksapi.model.Author;
import com.karankumar.booksapi.model.Book;
import com.karankumar.booksapi.model.BookFormat;
import com.karankumar.booksapi.model.BookGenre;
import com.karankumar.booksapi.model.Language;
import com.karankumar.booksapi.model.Publisher;
import com.karankumar.booksapi.repository.AuthorRepository;
import com.karankumar.booksapi.repository.BookRepository;
import com.karankumar.booksapi.repository.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class BooksApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BooksApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner populateData(AuthorRepository authorRepository,
                                          BookRepository bookRepository,
                                          PublisherRepository publisherRepository) {
        return args -> {
            Publisher publisher = new Publisher("Bloomsbury");

            Book book1 = createBook(
                    "Harry Potter and the Philosopher's stone",
                    "Philosopher's stone blurb",
                    1997,
                    "9781408810545"
            );
            bookRepository.save(book1);
            publisher.addBook(book1);

            Book book2 = createBook(
                    "Harry Potter and the Chamber of Secrets",
                    "Chamber of secrets blurb",
                    1998,
                    "1234567898765"
            );
            bookRepository.save(book2);

            Book book3 = createBook(
                    "The Hobbit",
                    "Hobbit blurb",
                    1937,
                    "1234567898761"
            );
            bookRepository.save(book3);
            Author author2 = new Author("J.R.R. Tolkien", Set.of(book3));
            author2.setAbout("Another fantastic author");
            authorRepository.save(author2);

            Author author = new Author("J.K. Rowling", Set.of(book1, book2, book3));
            author.setAbout("A fantastic author");
            authorRepository.save(author);

            publisherRepository.save(publisher);
        };
    }

    private Book createBook(String title, String blurb, int yearOfPublication, String isbn13) {
        Book book = new Book(
                title,
                Language.ENGLISH,
                blurb,
                BookGenre.FANTASY,
                BookFormat.PAPERBACK
        );
        book.setYearOfPublication(yearOfPublication);
        book.setIsbn13(isbn13);
        return book;
    }
}
