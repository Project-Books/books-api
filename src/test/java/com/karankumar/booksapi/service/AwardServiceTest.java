package com.karankumar.booksapi.service;

import com.karankumar.booksapi.model.award.Award;
import com.karankumar.booksapi.model.award.AwardName;
import com.karankumar.booksapi.repository.AwardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AwardServiceTest {
    private AwardService underTest;
    private AwardRepository awardRepository;

    @BeforeEach
    void setUp() {
        awardRepository = mock(AwardRepository.class);
        underTest = new AwardService(awardRepository);
    }

    @Test
    void canSave() {
        // given
        Award expected = new Award(AwardName.NOBEL_PRIZE, null, 1994, new HashSet<>());
        ArgumentCaptor<Award> awardArgumentCaptor = ArgumentCaptor.forClass(Award.class);

        // when
        underTest.save(expected);

        // then
        verify(awardRepository).save(awardArgumentCaptor.capture());
        assertThat(awardArgumentCaptor.getValue()).isEqualTo(expected);
    }

    @Test
    void findById_throwsNullPointerException_ifIdIsNull() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> underTest.findById(null));
        verify((awardRepository), never()).findById(anyLong());
    }

    @Test
    void canFindByNonNullId() {
        // given
        Long expected = 1L;
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        // when
        underTest.findById(expected);

        // then
        verify(awardRepository).findById(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(expected);
    }

    @Test
    void canFindAll() {
        underTest.findAll();
        verify(awardRepository).findAllAwards();
    }
}