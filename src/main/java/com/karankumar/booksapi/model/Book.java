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

import com.karankumar.booksapi.model.award.Award;
import com.karankumar.booksapi.model.genre.Genre;
import com.karankumar.booksapi.model.language.Lang;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

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
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "book", schema = "public")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(
                    name = "book_id",
                    foreignKey = @ForeignKey(name = "book_author_book_id_fkey")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "author_id",
                    foreignKey = @ForeignKey(name = "book_author_author_id_fkey")
            )
    )
    private Set<Author> authors = new HashSet<>();

    // If a book is written in different languages, they will each have their own ISBN.
    // Consequently, we will consider it to be a different book/edition
    @OneToOne
    private Lang lang;

    private String isbn10;

    private String isbn13;

    // TODO: should this be one to many?
    @OneToOne
    private Genre genre;

    private Integer yearOfPublication;

    @Column(nullable = false)
    private String blurb;

    @ToString.Exclude
    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Publisher> publishers = new HashSet<>();

    // One to one because a different book format warrants a new ISBN, so we will classify it as a
    // new book/edition
    @OneToOne
    private PublishingFormat publishingFormat;

    @OneToOne
    private Cover covers = new Cover();

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_award",
            joinColumns = @JoinColumn(
                    name = "book_id",
                    foreignKey = @ForeignKey(name = "book_award_book_id_fkey")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "award_id",
                    foreignKey = @ForeignKey(name = "book_award_award_id_fkey")
            )
    )
    private Set<Award> awards = new HashSet<>();

    public Book(@NonNull String title, @NonNull Lang lang,
                @NonNull String blurb, @NonNull Genre genre,
                @NonNull PublishingFormat publishingFormat) {
        this.title = title;
        this.lang = lang;
        this.blurb = blurb;
        this.genre = genre;
        this.publishingFormat = publishingFormat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
