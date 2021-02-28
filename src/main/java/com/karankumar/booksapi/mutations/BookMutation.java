package com.karankumar.booksapi.mutations;

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
    public Book addIsbn13(DataFetchingEnvironment dataFetchingEnvironment) {
        Optional<Book> book = bookService.findById(
                dataFetchingEnvironment.getArgument("bookId")
        );

        if (book.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }

        String isbn13 = dataFetchingEnvironment.getArgument("isbn13");

        if (isbn13.isBlank()) {
            throw new IllegalArgumentException("ISBN 13 cannot be empty");
        }

        book.get().setIsbn13(isbn13);
        return bookService.save(book.get());
    }
}
