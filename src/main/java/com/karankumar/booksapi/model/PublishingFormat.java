package com.karankumar.booksapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "publishing_format", schema = "public")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PublishingFormat {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    public enum Format {
        // IDs correspond the IDs in the associated database table
        EBOOK("eBook", 1),
        HARDCOVER("Hardcover", 2),
        PAPERBACK("Paperback", 3);

        private final String type;
        private final int id;

        Format(String type, int id) {
            this.type = type;
            this.id = id;
        }

        @Override
        public String toString() {
            return type;
        }
    }

    private String formatName;

    public PublishingFormat(Format format) {
        this.formatName = format.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        PublishingFormat bookFormat = (PublishingFormat) o;
        return Objects.equals(id, bookFormat.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
