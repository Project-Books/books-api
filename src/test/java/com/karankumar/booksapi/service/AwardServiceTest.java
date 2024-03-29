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

import com.karankumar.booksapi.model.award.Award;
import com.karankumar.booksapi.model.award.AwardName;
import com.karankumar.booksapi.repository.AwardRepository;
import com.karankumar.booksapi.repository.NativeQueryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AwardServiceTest {
    private AwardService underTest;
    private AwardRepository awardRepository;

    @BeforeEach
    void setUp() {
        awardRepository = mock(AwardRepository.class);
        NativeQueryRepository nativeQueryRepository = mock(NativeQueryRepository.class);
        underTest = new AwardService(awardRepository, nativeQueryRepository);
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
    void save_throwsNullPointerException_ifAwardIsNull() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> underTest.save(null));
        verify((awardRepository), never()).save(any());
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

    @Test
    void delete_throwsNullPointerException_ifAwardIsNull() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> underTest.deleteAward(null));
        verify((awardRepository), never()).deleteById(anyLong());
    }

    @Test
    void deleteById() {
        // given
        Award expected = new Award(AwardName.NOBEL_PRIZE, null, 1994, new HashSet<>());

        // when
        underTest.deleteAward(expected);

        // then
        verify(awardRepository, atMostOnce()).deleteById(expected.getId());
        assertThat(underTest.findById(1L)).isEmpty();
    }
}
