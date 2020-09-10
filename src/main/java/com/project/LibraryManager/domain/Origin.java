package com.project.LibraryManager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ORIGINS")
public class Origin {

    @Id
    @GeneratedValue
    @NotNull
    @Column
    private long id;

    @Column
    @NotNull
    private String title;

    @Column
    @NotNull
    private String author;

    @Column
    private int publishedYear;

    @Column
    private String isbn;


    @OneToMany(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER,
            targetEntity = Book.class,
            mappedBy = "origin"
    )
    private List<Book> books = new ArrayList<>();


    public Origin(String author, String title, int publishedYear) {
        this.author = author;
        this.title = title;
        this.publishedYear = publishedYear;
    }


    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public String toString() {
        return  "originId = " + id + "\n" +
                "title = " + title + "\n" +
                "author = " + author + "\n" +
                "publishedYear = " + publishedYear + "\n" +
                "isbn = " + isbn;
    }
}
