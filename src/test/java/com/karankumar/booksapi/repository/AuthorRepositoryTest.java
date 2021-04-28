/*
 * Copyright (C) 2020  Karan Kumar
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package com.karankumar.booksapi.repository;

import com.karankumar.booksapi.annotations.DataJpaIntegrationTest;
import com.karankumar.booksapi.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaIntegrationTest
@DisplayName("AuthorRepository should")
class AuthorRepositoryTest {
  
    private final AuthorRepository authorRepository;

    @Autowired
    AuthorRepositoryTest(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @BeforeEach
    void setUp() {
        authorRepository.deleteAll();
    }

    @Test
    @DisplayName("find saved authors")
    void findSavedAuthors() {
        // given
        Author author1 = createAndSaveAuthor("Kevlin", "Henney");
        Author author2 = createAndSaveAuthor("Trisha", "Gee");

        // when
        List<Author> result = authorRepository.findAllAuthors();

        // then
        assertThat(result).containsExactlyInAnyOrder(author1, author2);
    }

    private Author createAndSaveAuthor(String firstName, String lastName) {
        String fullName = firstName + " " + lastName;
        Author author = new Author(fullName, new HashSet<>());
        authorRepository.save(author);
        return author;
    }
}
