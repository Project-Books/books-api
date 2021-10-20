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

package com.karankumar.booksapi.model.award;

import com.karankumar.booksapi.model.Book;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable=false)
    private AwardName awardName;

    @Column
    private String category;

    @Column(nullable = false)
    private int year;

    @ManyToMany(mappedBy="awards", fetch = FetchType.LAZY)
    private Set<Book> books = new HashSet<>();

    public void addBook(@NonNull Book book) {
        books.add(book);
        book.getAwards().add(this);
    }

    public void removeBook(@NonNull Book book) {
        books.remove(book);
        book.getAwards().remove(this);
    }

    public Award(@NonNull AwardName awardName, String category, int year, Set<Book> books) {
        this.awardName = awardName;
        this.category = category;
        this.year = year;
        books.forEach(this::addBook);
    }
}
