package com.karankumar.booksapi.repository;

import com.karankumar.booksapi.model.PublishingFormat;
import org.springframework.data.repository.CrudRepository;

public interface FormatRepository extends CrudRepository<PublishingFormat, Long> {
}
