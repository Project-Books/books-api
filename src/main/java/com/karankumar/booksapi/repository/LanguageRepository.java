package com.karankumar.booksapi.repository;

import com.karankumar.booksapi.model.language.Lang;
import org.springframework.data.repository.CrudRepository;

public interface LanguageRepository extends CrudRepository<Lang, Long> {
}
