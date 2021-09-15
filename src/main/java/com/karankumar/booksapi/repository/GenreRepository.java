package com.karankumar.booksapi.repository;

import com.karankumar.booksapi.model.genre.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Long> {
}
