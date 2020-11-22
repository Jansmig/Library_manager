package com.project.LibraryManager.exception;

public class BookNotAvailableException extends LibraryException {

    public BookNotAvailableException(){
        super("Selected book is not available.");
    }
}
