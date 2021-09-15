package com.karankumar.booksapi.model.cover;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Table(name = "cover_file_type", schema = "public")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CoverFileType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    public enum ImageFileType {
        JPG(1),
        PNG(2);

        private final Integer id;

        ImageFileType(Integer id) {
            this.id = id;
        }

        public Integer getId() {
            return id;
        }

        public ImageFileType valueOf(Integer id) {
            for (ImageFileType value : values()) {
                if (value.id.equals(id)) {
                    return value;
                }
            }
            return null;
        }
    }

    @Enumerated(EnumType.ORDINAL)
    private ImageFileType fileType;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        CoverFileType that = (CoverFileType) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
