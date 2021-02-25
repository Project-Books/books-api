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

import com.karankumar.booksapi.model.Book;
import java.util.List;

import com.karankumar.booksapi.model.Publisher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
  
    @Query("SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors")
    List<Book> findAllBooks();
  
    @Query("SELECT a.books FROM Author a where a.firstName=?1 and a.lastName=?2")
    List<Book> findByAuthor(String firstName, String lastName);

    Book findBookByIsbn13(String isbn13);
  
    Book findByTitleIgnoreCase(String title);

    List<Book> findAllByPublishedBy(Publisher publisher);
}
