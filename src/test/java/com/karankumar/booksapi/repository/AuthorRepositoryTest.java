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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.karankumar.booksapi.model.Author;
import com.karankumar.booksapi.model.Book;
import com.karankumar.booksapi.model.BookFormat;
import com.karankumar.booksapi.model.BookGenre;
import com.karankumar.booksapi.model.Publisher;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AuthorRepositoryTest {
  
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private AuthorRepository authorRepository;
    
    @Test
    public void testFindAllBooks() {
      
        assertNotNull(bookRepository);
        assertNotNull(authorRepository);
        
        Author author1 = new Author("Kevlin", "Henney");
        Author author2 = new Author("Trisha", "Gee");
        Book book1 = new Book("97 Things Every Java Programmer Should Know",
            new Author[] {author1, author2});
        book1.setGenre(BookGenre.REFERENCE);
        book1.setYearOfPublication(2019);
        book1.setIsbn13("9781408670545");
        book1.setPublishedBy(Publisher.CAMBRIDGE_UNIVERSITY_PRESS);
        book1.setFormat(BookFormat.PAPERBACK);
        
        authorRepository.save(author1);
        authorRepository.save(author2);
        bookRepository.save(book1);
        
        List<Author> result = authorRepository.findAllAuthors();
        
        assertTrue(result.stream().anyMatch(e -> e.equals(author1)));
        assertTrue(result.stream().anyMatch(e -> e.equals(author2)));
        
        authorRepository.delete(author1);
        authorRepository.delete(author2);
        bookRepository.delete(book1);
        
    }

}
