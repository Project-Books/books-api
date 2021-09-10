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

package com.karankumar.booksapi.repository;

import com.karankumar.booksapi.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    @Query(value =
            "SELECT * FROM book b " +
            "WHERE b.publisher = (SELECT id from publisher WHERE name = :publisherName LIMIT 1) " +
            "UNION " +
            "SELECT * FROM book b WHERE b.title = :title " +
            "UNION " +
            "SELECT * FROM book b " +
            "WHERE b.isbn13 = :isbn13",
            nativeQuery = true
    )
    List<Book> findBooksByFilter(String isbn13, String title, String publisherName);

    @Query("SELECT a.books FROM Author a WHERE a.fullName=?1")
    List<Book> findByAuthor(String fullName);

    Book findBookByIsbn13(String isbn13);
  
    Book findByTitleIgnoreCase(String title);
    
    @Query(value="SELECT b.* FROM book b LEFT JOIN publisher_book pb ON pb.book_id = b.id " +
            "LEFT JOIN publisher p ON p.id = pb.publisher_id WHERE p.name LIKE :publisherName",
            nativeQuery=true
    )
    List<Book> findByPublisher(@Param("publisherName") String publisherName);
}
