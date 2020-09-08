package com.project.LibraryManager.domain;

import com.project.LibraryManager.exception.BookNotFoundException;
import com.project.LibraryManager.exception.StatusNotFoundException;

public enum BookStatus {

    AVAILABLE,
    RENTED,
    LOST;

    public static BookStatus convertStringToStatus(String string) throws BookNotFoundException {
        switch(string.toLowerCase()) {
            case "available":
                return AVAILABLE;
            case "rented":
                return RENTED;
            case "lost":
                return LOST;
            default:
                throw new StatusNotFoundException();
        }
    }

}
