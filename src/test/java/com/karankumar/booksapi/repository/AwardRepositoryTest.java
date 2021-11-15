package com.karankumar.booksapi.repository;

import com.karankumar.booksapi.annotations.DataJpaIntegrationTest;
import com.karankumar.booksapi.model.award.Award;
import com.karankumar.booksapi.model.award.AwardName;
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
    void findAllSavedAwards() {
        // given
        Award award1 = createAndSaveAward(AwardName.NOBEL_PRIZE, "Crime", 2010);
        Award award2 = createAndSaveAward(AwardName.DYLAN_PRIZE, null, 1994);

        // when
        List<Award> result = awardRepository.findAllAwards();

        // then
        assertThat(result).containsExactlyInAnyOrder(award1, award2);
    }

    private Award createAndSaveAward(AwardName awardName, String category, int year) {
        Award award = new Award(awardName, category, year, new HashSet<>());
        awardRepository.save(award);
        return award;
    }
}
