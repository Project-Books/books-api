package com.karankumar.booksapi.repository;

import com.karankumar.booksapi.model.language.Language;
import org.springframework.data.repository.CrudRepository;

public interface LanguageRepository extends CrudRepository<Language, Long> {
}
