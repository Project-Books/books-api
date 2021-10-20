package com.karankumar.booksapi.model.award;

import com.karankumar.booksapi.model.Book;
import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable=false)
    private AwardName awardName;

    @Column
    private String category;

    @Column(nullable = false)
    private int year;

    @ManyToMany(mappedBy="awards", fetch = FetchType.LAZY)
    private Set<Book> books = new HashSet<>();

    public void addBook(@NonNull Book book) {
        books.add(book);
        book.getAwards().add(this);
    }

    public void removeBook(@NonNull Book book) {
        books.remove(book);
        book.getAwards().remove(this);
    }

    public Award(@NonNull AwardName awardName, String category, int year, Set<Book> books) {
        this.awardName = awardName;
        this.category = category;
        this.year = year;
        books.forEach(this::addBook);
    }
}
