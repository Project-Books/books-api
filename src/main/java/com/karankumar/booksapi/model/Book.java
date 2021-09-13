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

import com.karankumar.booksapi.model.cover.BookCover;
import com.karankumar.booksapi.model.enums.BookGenre;
import com.karankumar.booksapi.model.enums.LanguageName;
import com.karankumar.booksapi.model.format.Format;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
            joinColumns = @JoinColumn(
                    name = "book_id",
                    foreignKey = @ForeignKey(name = "book_author_book_id_fk")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "author_id",
                    foreignKey = @ForeignKey(name = "book_author_author_id_fk")
            )
    )
    private Set<Author> authors = new HashSet<>();

    @Column(nullable = false)
    private LanguageName languageName;

    private String isbn10;

    private String isbn13;

    @Column(nullable = false)
    private BookGenre genre;

    private Integer yearOfPublication;

    @Column(nullable = false)
    private String blurb;

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Publisher> publishers = new HashSet<>();

    @OneToOne
    private Format format;

    @OneToMany(mappedBy = "book")
    private Set<BookCover> bookCover = new HashSet<>();

    public Book(@NonNull String title, @NonNull LanguageName languageName, @NonNull String blurb,
                @NonNull BookGenre genre, @NonNull Format format) {
        this.title = title;
        this.languageName = languageName;
        this.blurb = blurb;
        this.genre = genre;
        this.format = format;
    }
}
