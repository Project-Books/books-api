package com.karankumar.booksapi.model.cover;

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
import javax.persistence.Table;
import java.util.Objects;

@Table(name = "image_file_type", schema = "public")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ImageFileType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    public enum FileType {
        JPG(1),
        PNG(2);

        private final Integer id;

        FileType(Integer id) {
            this.id = id;
        }

        public Integer getId() {
            return id;
        }

        public FileType valueOf(Integer id) {
            for (FileType value : values()) {
                if (value.id.equals(id)) {
                    return value;
                }
            }
            return null;
        }
    }

    @Enumerated(EnumType.ORDINAL)
    private FileType fileType;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        ImageFileType that = (ImageFileType) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
