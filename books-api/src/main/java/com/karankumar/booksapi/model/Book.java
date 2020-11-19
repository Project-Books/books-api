package com.karankumar.booksapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    @Setter
    private String title;

    @Setter
    private String isbn;

    @Setter
    private BookGenre genre;

    @Setter
    private Integer yearOfPublication;

}
