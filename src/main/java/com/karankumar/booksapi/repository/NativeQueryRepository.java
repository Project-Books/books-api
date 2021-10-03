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
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Set;

@Repository
public class NativeQueryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void deleteAllAuthorAssociations(Long authorId) {
        entityManager.createNativeQuery("DELETE FROM book_author WHERE author_id = :authorId")
                .setParameter("authorId", authorId)
                .executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public Set<Book> getAllBooksAuthorWroteAlone(Long authorId) {
        return new HashSet<Book>(entityManager.createNativeQuery("" +
                "SELECT b.* FROM book_author ba " +
                "JOIN book b ON ba.book_id = b.id " +
                "JOIN book_author ba2 ON b.id = ba2.book_id " +
                "WHERE ba2.author_id = :authorId " +
                "GROUP BY ba.book_id, b.id " +
                "HAVING count(ba.author_id) = 1", Book.class)
                .setParameter("authorId", authorId)
                .getResultList());
    }
}
