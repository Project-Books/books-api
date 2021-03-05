package com.karankumar.booksapi.mutations;

import com.karankumar.booksapi.exception.InvalidISBN10Exception;
import com.karankumar.booksapi.exception.InvalidISBN13Exception;
import com.karankumar.booksapi.model.Book;
import com.karankumar.booksapi.service.BookService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@DgsComponent
public class BookMutation {
    private final BookService bookService;

    public BookMutation(BookService bookService) {
        this.bookService = bookService;
    }

    // TODO: change this to update a book rather than updating a specific field
    @DgsData(parentType = "Mutation", field = "addIsbn13")
    public Book addIsbn13(DataFetchingEnvironment dataFetchingEnvironment)
            throws InvalidISBN10Exception, InvalidISBN13Exception {
        Optional<Book> optionalBook = bookService.findById(
                dataFetchingEnvironment.getArgument("bookId")
        );

        if (optionalBook.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }

        String isbn13 = dataFetchingEnvironment.getArgument("isbn13");
        Book book = optionalBook.get();
        book.setIsbn13(isbn13);
        return bookService.save(optionalBook.get());
    }
}
