/*
 * Copyright (C) 2020  Karan Kumar
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

package com.karankumar.booksapi.repository;

import com.karankumar.booksapi.model.Author;
import com.karankumar.booksapi.model.Book;
import com.karankumar.booksapi.model.BookFormat;
import com.karankumar.booksapi.model.BookGenre;
import com.karankumar.booksapi.model.Language;
import com.karankumar.booksapi.model.Publisher;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@DisplayName("AuthorRepository should")
public class AuthorRepositoryTest {
  
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    
    private final List<Book> books;
    private final List<Author> authors; 
  
    @Autowired
    public AuthorRepositoryTest(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.books = new ArrayList<>();
        this.authors = new ArrayList<>();
    }
  
    @Test
    @DisplayName("find saved authors")
    public void findSavedAuthors() {
        saveBookAndAuthors();
    
        List<Author> result = authorRepository.findAllAuthors();
    
        assertThat(result.stream().anyMatch(e -> e.equals(authors.get(0))))
            .isTrue();
        
        assertThat(result.stream().anyMatch(e -> e.equals(authors.get(1))))
            .isTrue();
    }
  
    private void saveBookAndAuthors() {
        Author author1 = new Author("Kevlin", "Henney");
        Author author2 = new Author("Trisha", "Gee");
        Book book1 = new Book("97 Things Every Java Programmer Should Know", 
            new Author[] { author1, author2 }, Language.ENGLISH);
        book1.setGenre(BookGenre.REFERENCE);
        book1.setYearOfPublication(2019);
        book1.setIsbn13("9781408670545");
        book1.setPublishedBy(Publisher.CAMBRIDGE_UNIVERSITY_PRESS);
        book1.setFormat(BookFormat.PAPERBACK);
    
        authors.add(author1);
        authors.add(author2);
        books.add(book1);
    
        authorRepository.saveAll(authors);
        bookRepository.saveAll(books);
    }
  
    @AfterEach
    public void tearDown() {
        authorRepository.deleteAll(authors);
        bookRepository.deleteAll(books);
    }

}
