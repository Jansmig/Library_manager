package com.project.LibraryManager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class RentalDtoResponse {

    private long id;
    private long bookId;
    private String bookTitle;
    private long userId;
    private String userFirstName;
    private String userLastName;
    private LocalDateTime rentalDate;
    private LocalDateTime returnDate;
    boolean active;

}
