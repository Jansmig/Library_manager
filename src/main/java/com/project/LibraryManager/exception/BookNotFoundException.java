package com.project.LibraryManager.exception;

public class BookNotFoundException extends LibraryException {

    public BookNotFoundException(){
        super("Book with selected ID does not exist.");
    }
}
