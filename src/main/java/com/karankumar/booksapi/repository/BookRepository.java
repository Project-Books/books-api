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
import com.karankumar.booksapi.model.award.AwardName;
import com.karankumar.booksapi.model.genre.GenreName;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    // TODO: this is kept in for quick testing, but will be removed at a later date
    @Query(value =
            "SELECT b FROM Book b " +
            "LEFT JOIN FETCH b.authors " +
            "LEFT JOIN FETCH b.publishers " +
            "LEFT JOIN FETCH b.awards"
    )
    List<Book> findAllBooks();
  
    @Query("SELECT a.books FROM Author a where a.fullName=?1")
    List<Book> findByAuthor(String fullName);

    Book findBookByIsbn13(String isbn13);

    @Query(value = "SELECT b FROM Book b WHERE lower(b.title) = lower(:title)")
    Book findByTitleIgnoreCase(String title);
    
    @Query(value =
            "SELECT b.* FROM book b " +
            "LEFT JOIN publisher_book pb ON pb.book_id = b.id " +
            "LEFT JOIN publisher p ON p.id = pb.publisher_id " +
            "WHERE p.name LIKE :publisherName",
            nativeQuery = true
    )
    List<Book> findByPublisher(@Param("publisherName") String publisherName);

    @Query("SELECT b FROM Book b WHERE b.genre.name LIKE ?1")
    List<Book> findByGenre(String genreName);

    @Query("SELECT a.books FROM Award a where a.awardName=?1")
    List<Book> findByAwardName(AwardName awardName);
}
