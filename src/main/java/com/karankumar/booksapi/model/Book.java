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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    @Column(nullable = false)
    private Language language;

    private String isbn10;

    private String isbn13;

    @Column(nullable = false)
    private BookGenre genre;

    private Integer yearOfPublication;

    @Column(nullable = false)
    private String blurb;

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private Set<Publisher> publishers = new HashSet<>();

    @Column(nullable = false)
    private BookFormat format;

    public Book(@NonNull String title, @NonNull Author[] authors, @NonNull Language language,
                @NonNull String blurb, @NonNull BookGenre genre, @NonNull BookFormat format) {
        this.title = title;
        Arrays.stream(authors).forEach(this::addAuthor);
        this.language = language;
        this.blurb = blurb;
        this.genre = genre;
        this.format = format;
    }

    public void addAuthor(@NonNull Author author) {
        authors.add(author);
        author.getBooks().add(this);
    }
}
