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

import com.karankumar.booksapi.model.Author;
import com.karankumar.booksapi.repository.AuthorRepository;
import com.karankumar.booksapi.repository.BookRepository;
import com.karankumar.booksapi.repository.NativeQueryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {
    private AuthorService underTest;
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private NativeQueryRepository nativeQueryRepository;

    @BeforeEach
    void setUp() {
        authorRepository = mock(AuthorRepository.class);
        underTest = new AuthorService(authorRepository, bookRepository, nativeQueryRepository);
        nativeQueryRepository = mock(NativeQueryRepository.class);
        bookRepository = mock(BookRepository.class);
    }

    @Test
    void canSave() {
        // given
        Author expected = new Author("test", new HashSet<>());
        ArgumentCaptor<Author> authorArgumentCaptor = ArgumentCaptor.forClass(Author.class);

        // when
        underTest.save(expected);

        // then
        verify(authorRepository).save(authorArgumentCaptor.capture());
        assertThat(authorArgumentCaptor.getValue()).isEqualTo(expected);
    }

    @Test
    void findById_throwsNullPointerException_ifIdIsNull() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> underTest.findById(null));
        verify((authorRepository), never()).findById(anyLong());
    }

    @Test
    void canFindByNonNullId() {
        // given
        Long expected = 1L;
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        // when
        underTest.findById(expected);

        // then
        verify(authorRepository).findById(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(expected);
    }

    @Test
    void canFindAll() {
        underTest.findAll();
        verify(authorRepository).findAllAuthors();
    }
}