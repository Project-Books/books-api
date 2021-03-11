package com.karankumar.booksapi.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookSeries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String seriesName;

    @OneToMany(mappedBy = "bookSeries")
    private List<BookSeriesMapping> bookSeriesMapping;

    public BookSeries(String seriesName){
        this.seriesName = seriesName;
    }
}
