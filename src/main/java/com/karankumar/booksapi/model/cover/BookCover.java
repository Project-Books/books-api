package com.karankumar.booksapi.model.cover;

import com.karankumar.booksapi.model.Book;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

/**
 * We store a partial path in our blob storage and then dynamically add the prefix needed.
 * We only accept the .jpg image file type
 */
@Table(name = "book_cover")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BookCover {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    private CoverFileType smallFileType;

    @OneToOne
    private CoverFileType mediumFileType;

    @OneToOne
    private CoverFileType largeFileType;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private static final String PREFIX = "https://bapiimagesdev.blob.core.windows.net/covers/";

    public String getPathToSmall() {
        final String suffix = "/small.jpg";
        return PREFIX + id + suffix;
    }

    public String getPathToMedium() {
        final String suffix = "/medium.jpg";
        return PREFIX + id + suffix;
    }

    public String getPathToLarge() {
        final String suffix = "/large.jpg";
        return PREFIX + id + suffix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        BookCover bookCover = (BookCover) o;
        return Objects.equals(id, bookCover.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}