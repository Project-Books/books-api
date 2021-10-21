/*
 * Copyright (C) 2021  Karan Kumar
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
package com.karankumar.booksapi.service;

import com.karankumar.booksapi.annotations.DataJpaIntegrationTest;
import com.karankumar.booksapi.model.Book;
import com.karankumar.booksapi.model.PublishingFormat;
import com.karankumar.booksapi.model.award.Award;
import com.karankumar.booksapi.model.award.AwardName;
import com.karankumar.booksapi.model.genre.Genre;
import com.karankumar.booksapi.model.genre.GenreName;
import com.karankumar.booksapi.model.language.Lang;
import com.karankumar.booksapi.model.language.LanguageName;
import com.karankumar.booksapi.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DataJpaIntegrationTest
@Import({NativeQueryRepository.class, AwardService.class})
public class AwardServiceIntegrationTest {

    private final AwardService underTest;
    private final AwardRepository awardRepository;
    private final GenreRepository genreRepository;
    private final LanguageRepository languageRepository;
    private final PublishingFormatRepository publishingFormatRepository;
    private final BookRepository bookRepository;

    @Autowired
    public AwardServiceIntegrationTest(AwardService underTest, AwardRepository awardRepository,
                                        GenreRepository genreRepository,
                                        LanguageRepository languageRepository,
                                        PublishingFormatRepository publisherRepository,
                                        BookRepository bookRepository) {
        this.underTest = underTest;
        this.awardRepository = awardRepository;
        this.genreRepository = genreRepository;
        this.languageRepository = languageRepository;
        this.publishingFormatRepository = publisherRepository;
        this.bookRepository = bookRepository;
    }

    private Book createBook(String title) {
        Genre genre = new Genre(GenreName.SATIRE);
        genreRepository.save(genre);
        Lang lang = new Lang(LanguageName.ENGLISH);
        languageRepository.save(lang);
        PublishingFormat publishingFormat = new PublishingFormat(PublishingFormat.Format.HARDCOVER);
        publishingFormatRepository.save(publishingFormat);
        return new Book(
                title,
                lang,
                "Test Blurb",
                genre,
                publishingFormat
        );
    }

    @Test
    public void deleteAward_whenAwardHasNoRelations() {
        // given
        Award award = new Award(AwardName.PORTICO_PRIZE, null, 1994, Collections.emptySet());
        awardRepository.save(award);

        // when
        underTest.deleteAward(award);

        //then
        Assertions.assertThat(awardRepository.findById(award.getId())).isNotPresent();
    }

    @Test
    public void deleteAward_shouldNotDeleteBook() {
        // given
        Book book = createBook("Test book");
        Award award = new Award(AwardName.PORTICO_PRIZE, null, 1994, new HashSet<>(Collections.singletonList(book)));
        bookRepository.save(book);
        awardRepository.save(award);

        // when
        underTest.deleteAward(award);

        // then
        assertSoftly(softly -> {
            softly.assertThat(awardRepository.findById(award.getId())).isNotPresent();
            softly.assertThat(bookRepository.findById(book.getId())).isPresent();
        });
    }
}
