package com.karankumar.booksapi.model;

import com.karankumar.booksapi.model.award.Award;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("Award should")
class AwardTest {
    @Test
    @DisplayName("throw a Null Pointer Exception on an attempt to create with a null awardName")
    void notAcceptNullTitle() {
        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(() -> new Award(null, null, 1994, Sets.newHashSet()));
    }
}
