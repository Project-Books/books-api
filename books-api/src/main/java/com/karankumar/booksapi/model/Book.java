package com.karankumar.booksapi.model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Book extends BaseEntity {
    private String title;

    private String isbn;

    private BookGenre genre;

    private Integer yearOfPublication;
}
