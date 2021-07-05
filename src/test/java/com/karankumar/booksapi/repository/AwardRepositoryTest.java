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
import com.karankumar.booksapi.model.Award;
import com.karankumar.booksapi.model.AwardName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaIntegrationTest
@DisplayName("AwardRepository should")
class AwardRepositoryTest {

    private final AwardRepository awardRepository;

    @Autowired
    AwardRepositoryTest(AwardRepository awardRepository) {
        this.awardRepository = awardRepository;
    }

    @BeforeEach
    void setUp() {
        awardRepository.deleteAll();
    }

    @Test
    @DisplayName("find saved awards")
    void findSavedAuthors() {
        // given
        Award award1 = createAndSaveAuthor(AwardName.NOBEL_PRIZE, "Crime", 2010);
        Award award2 = createAndSaveAuthor(AwardName.DYLAN_PRIZE, null, 1994);

        // when
        List<Award> result = awardRepository.findAllAwards();

        // then
        assertThat(result).containsExactlyInAnyOrder(award1, award2);
    }

    private Award createAndSaveAuthor(AwardName awardName, String category, int year) {
        Award award = new Award(awardName, category, year, new HashSet<>());
        awardRepository.save(award);
        return award;
    }
}
