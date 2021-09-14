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
import javax.persistence.Transient;
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
    @JoinColumn(name = "id")
    private Book book;

    @Transient
    private String pathToSmall;

    @Transient
    private String pathToMedium;

    @Transient
    private String pathToLarge;

    // TODO: configure to use a different prefix for prod
    private static final String PREFIX = "https://bapiimagesdev.blob.core.windows.net/covers/";
    private static final String SMALL_SUFFIX = "/small";
    private static final String MEDIUM_SUFFIX = "/medium";
    private static final String LARGE_SUFFIX = "/large";

    private String getFileType(CoverFileType fileType) {
        switch (fileType.getFileType()) {
            case JPG:
                return ".jpg";
            case PNG:
                return ".png";
            default:
                return "";
        }
    }

    public String getPathToSmall() {
        return PREFIX + id + SMALL_SUFFIX + getFileType(smallFileType);
    }

    public String getPathToMedium() {
        return PREFIX + id + MEDIUM_SUFFIX + getFileType(mediumFileType);
    }

    public String getPathToLarge() {
        return PREFIX + id + LARGE_SUFFIX + getFileType(largeFileType);
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