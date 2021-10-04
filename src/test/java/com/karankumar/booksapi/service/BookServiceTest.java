package com.karankumar.booksapi.service;

import com.karankumar.booksapi.exception.InvalidISBN10Exception;
import com.karankumar.booksapi.exception.InvalidISBN13Exception;
import com.karankumar.booksapi.model.Book;
import com.karankumar.booksapi.model.PublishingFormat;
import com.karankumar.booksapi.model.genre.Genre;
import com.karankumar.booksapi.model.genre.GenreName;
import com.karankumar.booksapi.model.language.Lang;
import com.karankumar.booksapi.model.language.LanguageName;
import com.karankumar.booksapi.repository.BookRepository;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class BookServiceTest {

    private BookService underTest;
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        underTest = new BookService(bookRepository);
    }

    @Test
    void save_throwsNullPointerException_ifBookIsNull() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> underTest.save(null));
        verify(bookRepository, never()).save(any());
    }

    @Test
    void save_throwsInvalidIsbn10_ifIsbn10IsInvalid() {
        // given
        Book bookWithInvalidIsbn10 = new Book(
                "title",
                new Lang(LanguageName.ENGLISH),
                "blurb",
                new Genre(GenreName.CRIME),
                new PublishingFormat()
        );
        bookWithInvalidIsbn10.setIsbn10("1234567890");

        // when
        ThrowableAssert.ThrowingCallable throwingCallable =
                () -> underTest.save(bookWithInvalidIsbn10);

        // then
        assertThatExceptionOfType(InvalidISBN10Exception.class)
                .isThrownBy(throwingCallable);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void save_throwsInvalidIsbn13_ifIsbn13IsInvalid() {
        // given
        Book bookWithInvalidIsbn13 = new Book(
                "title",
                new Lang(LanguageName.ENGLISH),
                "blurb",
                new Genre(GenreName.CRIME),
                new PublishingFormat()
        );
        bookWithInvalidIsbn13.setIsbn13("1234567890123");

        assertThatExceptionOfType(InvalidISBN13Exception.class)
                .isThrownBy(() -> underTest.save(bookWithInvalidIsbn13));
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void canSaveBookWithValidIsbn10() throws InvalidISBN13Exception, InvalidISBN10Exception {
        // given
         Book bookWithValidIsbn10 = new Book(
                 "title",
                 new Lang(LanguageName.ENGLISH),
                 "blurb",
                 new Genre(GenreName.CRIME),
                 new PublishingFormat()
        );
        bookWithValidIsbn10.setIsbn10("1843560283");
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        // when
        underTest.save(bookWithValidIsbn10);

        // then
        verify(bookRepository).save(bookArgumentCaptor.capture());
        assertThat(bookArgumentCaptor.getValue()).isEqualTo(bookWithValidIsbn10);
    }

    @Test
    void canSaveBookWithValidIsbn13() throws InvalidISBN13Exception, InvalidISBN10Exception {
        // given
        Book book = new Book(
                "title",
                new Lang(LanguageName.ENGLISH),
                "blurb",
                new Genre(GenreName.CRIME),
                new PublishingFormat()
        );
        book.setIsbn13("9783161484100");
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        // when
        underTest.save(book);

        // then
        verify(bookRepository).save(bookArgumentCaptor.capture());
        assertThat(bookArgumentCaptor.getValue()).isEqualTo(book);
    }

    @Test
    void canSaveBookWithoutIsbn10Or13() throws InvalidISBN13Exception, InvalidISBN10Exception {
        // given
        Book book = new Book(
                "title",
                new Lang(LanguageName.ENGLISH),
                "blurb",
                new Genre(GenreName.CRIME),
                new PublishingFormat()
        );
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        // when
        underTest.save(book);

        // then
        verify(bookRepository).save(bookArgumentCaptor.capture());
        assertThat(bookArgumentCaptor.getValue()).isEqualTo(book);
    }

    @Test
    void canFindByNonNullId() {
        // given
        Long expected = 1L;
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        // when
        underTest.findById(expected);

        // then
        verify(bookRepository).findById(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(expected);
    }

    @Test
    void canFindAll() {
        underTest.findAll();
        verify(bookRepository).findAllBooks();
    }

    @Test
    void findByAuthor_throwsNullPointerException_ifAuthorIsNull() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> underTest.findByAuthor(null));
        verify(bookRepository, never()).findByAuthor(anyString());
    }

    @Test
    void canFindByAuthorIfStringNotNull() {
        // given
        String expected = "test";
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        // when
        underTest.findByAuthor(expected);

        // then
        verify(bookRepository).findByAuthor(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue()).isEqualTo(expected);
    }

    @Test
    void findBookByIsbn13_throwsNullPointerException_ifIsbnIsNull() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> underTest.findBookByIsbn13(null));
        verify(bookRepository, never()).findBookByIsbn13(anyString());
    }

    @Test
    void canFindByNonNullIsbn13() {
        // given
        String expected = "isbn";
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        // when
        underTest.findBookByIsbn13(expected);

        // then
        verify(bookRepository).findBookByIsbn13(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue()).isEqualTo(expected);
    }

    @Test
    void findByTitle_throwsNullPointerException_ifTitleIsNull() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> underTest.findByTitle(null));
        verify(bookRepository, never()).findByTitleIgnoreCase(anyString());
    }

    @Test
    void canFindByNonNullTitle() {
        // given
        String expected = "test";
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        // when
        underTest.findByTitle(expected);

        // then
        verify(bookRepository).findByTitleIgnoreCase(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue()).isEqualTo(expected);
    }

    @Test
    void deleteBook_throwsNullPointerException_ifIdIsNull() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> underTest.deleteBook(null));
        verify(bookRepository, never()).delete(any(Book.class));
    }

    @Test
    void canDeleteBookIfIdIsNotNull() {
        // given
        Long expected = 1L;
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        // when
        underTest.deleteBook(expected);

        // then
        verify(bookRepository).deleteById(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(expected);
    }

    @Test
    void findByPublisher_throwsNullPointerException_ifPublisherNameIsNull() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> underTest.findByPublisher(null));
        verify(bookRepository, never()).findByPublisher(anyString());
    }

    @Test
    void canFindByNonNullPublisherName() {
        // given
        String publisherName = "Penguin";
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        // when
        underTest.findByPublisher(publisherName);

        // then
        verify(bookRepository).findByPublisher(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue()).isEqualTo(publisherName);
    }

    @Test
    void canFindByNonNullGenreType() {
        // given
        GenreName genreName = GenreName.PSYCHOLOGY;
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        // when
        underTest.findByGenre(genreName);

        // then
        verify(bookRepository).findByGenre(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue()).isEqualTo(genreName.getGenre());
    }
}