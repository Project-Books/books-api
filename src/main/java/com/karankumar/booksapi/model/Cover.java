package com.karankumar.booksapi.model.cover;

import com.karankumar.booksapi.model.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.FetchType;
import java.util.Objects;

/**
 * We store a partial path in our blob storage and then dynamically add the prefix needed.
 * We only accept the .jpg image file type
 */
@Table(name = "cover", schema = "public")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Cover {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String smallUrl;
    private String mediumUrl;
    private String largeUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book", nullable = false)
    @ToString.Exclude
    private Book book;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Cover bookCover = (Cover) o;
        return Objects.equals(id, bookCover.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}