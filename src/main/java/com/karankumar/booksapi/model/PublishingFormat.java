package com.karankumar.booksapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PublishingFormat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum Type {
        // IDs correspond the IDs in the associated database table
        EBOOK("eBook", 1),
        HARDCOVER("Hardcover", 2),
        PAPERBACK("Paperback", 3);

        private final String type;
        private final int id;

        Type(String type, int id) {
            this.type = type;
            this.id = id;
        }

        @Override
        public String toString() {
            return type;
        }
    }

    @Enumerated(EnumType.ORDINAL)
    private Type type;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        FormatType
                bookFormat = (FormatType) o;
        return Objects.equals(id, bookFormat.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
