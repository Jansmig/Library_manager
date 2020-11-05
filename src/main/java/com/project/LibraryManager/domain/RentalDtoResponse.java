package com.project.LibraryManager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
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
