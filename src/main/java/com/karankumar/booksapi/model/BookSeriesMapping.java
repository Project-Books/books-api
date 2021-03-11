package com.karankumar.booksapi.model;

import lombok.*;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity

@Table(
        name="Book_To_BookSeries_Mapping",
        uniqueConstraints =
                @UniqueConstraint( columnNames = {"book_id","book_series_id","serialNumber"} )
)

public class BookSeriesMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @ManyToOne
    private BookSeries bookSeries;

    @ManyToOne
    private Book book;

    @Column(nullable = false, name = "serialNumber")
    private int serialNumber;

    public BookSeriesMapping(@NonNull BookSeries bookSeries, @NonNull Book bookInSeries, @NonNull int serialNumber) {
        this.bookSeries = bookSeries;
        this.book = bookInSeries;
        this.serialNumber = serialNumber;
    }
}
