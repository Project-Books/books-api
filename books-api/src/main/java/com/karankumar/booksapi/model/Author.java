package com.karankumar.booksapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Author {
    @Id
    @GeneratedValue
    private Long id;

    @Setter
    private String firstName;

    @Setter
    private String lastName;
}
