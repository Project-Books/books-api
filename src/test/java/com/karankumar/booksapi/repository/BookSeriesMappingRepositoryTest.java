package com.karankumar.booksapi.repository;

import com.karankumar.booksapi.annotations.DataJpaIntegrationTest;
import com.karankumar.booksapi.model.Author;
import com.karankumar.booksapi.model.Book;
import com.karankumar.booksapi.model.BookFormat;
import com.karankumar.booksapi.model.BookGenre;
import com.karankumar.booksapi.model.BookSeries;
import com.karankumar.booksapi.model.BookSeriesMapping;
import com.karankumar.booksapi.model.Language;
import com.karankumar.booksapi.model.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaIntegrationTest
@DisplayName("BookSeriesMappingRepository should")
public class BookSeriesMappingRepositoryTest {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookSeriesRepository bookSeriesRepository;
    private final BookSeriesMappingRepository bookSeriesMappingRepository;

    @Autowired
    BookSeriesMappingRepositoryTest(BookRepository bookRepository, AuthorRepository authorRepository,
                                    BookSeriesRepository bookSeriesRepository, BookSeriesMappingRepository bookSeriesMappingRepository){

        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookSeriesRepository = bookSeriesRepository;
        this.bookSeriesMappingRepository = bookSeriesMappingRepository;
    }

    @BeforeEach
    void setUp() {
        bookSeriesMappingRepository.deleteAll();
        authorRepository.deleteAll();
        bookRepository.deleteAll();
        bookSeriesRepository.deleteAll();
    }

    @Test
    @DisplayName("return all books in a series.")
    void fetchAllBooksInBookSeries(){

        BookSeries bs = createBookSeriesMapping();
        List<Book> assertion = bookSeriesMappingRepository.getAllBooksByBookSeries(bs);

        List<Book> bookList = new ArrayList<>();
        bookList.add(bookRepository.findBookByIsbn13("9781408810545"));
        bookList.add(bookRepository.findBookByIsbn13("1234567898765"));

        assertThat(assertion).hasSize(bookList.size()).hasSameElementsAs(bookList);
    }

    private BookSeries createBookSeriesMapping(){

        Author author = new Author("J.K.", "Rowling");
        author.setAbout("A fantastic author");
        authorRepository.save(author);

        Book book1 = new Book(
                "Harry Potter and the Philosopher's stone", new Author[]{author},
                Language.ENGLISH, "Sample blurb value",
                BookGenre.FANTASY, BookFormat.PAPERBACK
        );
        book1.setYearOfPublication(1997);
        book1.setIsbn13("9781408810545");
        book1.setPublishedBy(Publisher.BLOOMSBURY);
        bookRepository.save(book1);

        Book book2 = new Book(
                "Harry Potter and the Chamber of Secrets",
                new Author[]{author},
                Language.ENGLISH,
                "Sample blurb value",
                BookGenre.FANTASY,
                BookFormat.PAPERBACK
        );
        book2.setIsbn13("1234567898765");
        book2.setGenre(BookGenre.FANTASY);
        book2.setPublishedBy(Publisher.BLOOMSBURY);
        book2.setFormat(BookFormat.PAPERBACK);
        bookRepository.save(book2);

        BookSeries bookSeries = new BookSeries("Harry Potter Series");
        bookSeriesRepository.save(bookSeries);

        bookSeriesMappingRepository.save(new BookSeriesMapping(bookSeries, book1, 1));
        bookSeriesMappingRepository.save(new BookSeriesMapping(bookSeries, book2, 2));

        return bookSeries;
    }
}
