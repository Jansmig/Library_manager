package com.project.LibraryManager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static com.project.LibraryManager.domain.BookStatus.*;

@AllArgsConstructor
@Getter
@Setter
@Entity(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue
    @NotNull
    @Column
    private long id;

    @ManyToOne
    @JoinColumn
    private Origin origin;

    @Column
    private BookStatus bookStatus;


    public Book(final Origin origin) {
        this.origin = origin;
        this.bookStatus = AVAILABLE;
    }

    public Book() {
        this.setAvailable();
    }

    public void setRented() {
        this.bookStatus = RENTED;
    }

    public void setAvailable() {
        this.bookStatus = AVAILABLE;
    }

    public void setLost() {
        this.bookStatus = LOST;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }


    @Override
    public String toString() {
        return "bookId = " + id + "\n" +
                "title = " + origin.getTitle() + "\n" +
                "bookStatus = " + bookStatus;
    }
}
