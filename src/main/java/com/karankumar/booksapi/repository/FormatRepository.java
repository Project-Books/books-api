package com.karankumar.booksapi.repository;

import com.karankumar.booksapi.model.format.Format;
import org.springframework.data.repository.CrudRepository;

public interface FormatRepository extends CrudRepository<Format, Long> {
}
