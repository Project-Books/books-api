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
import com.karankumar.booksapi.model.BookSeries;
import com.karankumar.booksapi.model.BookSeriesMapping;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookSeriesMappingRepository extends CrudRepository<BookSeriesMapping, Long> {
    @Query("SELECT a.book FROM BookSeriesMapping a WHERE a.bookSeries = ?1")
    List<Book> getAllBooksByBookSeries(BookSeries bookSeries);
}
