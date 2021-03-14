/*
The book project lets a user keep track of different books they would like to read, are currently reading, have read or did not finish.
Copyright (C) 2020  Karan Kumar

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
PURPOSE.  See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program.
If not, see <https://www.gnu.org/licenses/>.
*/

package com.karankumar.booksapi.model;

import lombok.*;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name="Book_To_BookSeries_Mapping",
        uniqueConstraints = {
                @UniqueConstraint( name = "UK_bookSeriesId_bookId", columnNames = {"book_series_id","book_id"} ),
                @UniqueConstraint( name = "UK_bookSeriesId_serialNumber", columnNames = {"book_series_id","serial_number"} )
        }
)

public class BookSeriesMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(optional = false)
    private BookSeries bookSeries;

    @ManyToOne(optional = false)
    private Book book;

    @Column(nullable = false, name="serial_number")
    private Integer serialNumber;

    public BookSeriesMapping(@NonNull BookSeries bookSeries, @NonNull Book bookInSeries, @NonNull Integer serialNumber) {
        this.bookSeries = bookSeries;
        this.book = bookInSeries;
        this.serialNumber = serialNumber;
    }
}
