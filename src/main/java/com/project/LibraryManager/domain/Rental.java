package com.project.LibraryManager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "RENTALS")
public class Rental {

    @Id
    @GeneratedValue
    @NotNull
    @Column
    private long id;

    @OneToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    private LocalDateTime rentalDate;
    private LocalDateTime returnDate;
    boolean active;

    public Rental(User user, Book book) {
        this.user = user;
        this.book = book;
        book.setRented();
        this.rentalDate = LocalDateTime.now();
        this.active = true;
    }

    public void returnBook() {
        this.returnDate = LocalDateTime.now();
        this.book.setAvailable();
        this.active = false;
    }

    @Override
    public String toString() {
        return  "rental Id = " + id + "\n" +
                "book title = " + book.getOrigin().getTitle() + "\n" +
                "book Id = " + book.getId() + "\n" +
                "user = " + user.getFirstName() + " " + user.getLastName() + "\n" +
                "user Id = " + user.getId() + "\n" +
                "rentalDate = " + rentalDate + "\n" +
                "returnDate = " + returnDate + "\n" +
                "active = " + active;
    }
}
