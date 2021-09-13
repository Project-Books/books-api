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

package com.karankumar.booksapi.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode()
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String fullName;

    @ToString.Exclude
    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Book> books = new HashSet<>();

    private String about;

    public Author(@NonNull String fullName, @NonNull Set<Book> books) {
        this.fullName = fullName;
        books.forEach(this::addBook);
    }

    public void addBook(@NonNull Book book) {
        books.add(book);
        book.getAuthors().add(this);
    }

    public void removeBook(@NonNull Book book) {
        books.remove(book);
        book.getAuthors().remove(this);
    }
}
