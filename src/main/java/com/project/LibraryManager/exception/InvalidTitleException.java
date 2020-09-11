package com.project.LibraryManager.exception;

public class InvalidTitleException extends LibraryException{

    public InvalidTitleException() {
        super("Phrase used for searching titles needs to be at least 3 characters long.");
    }
}
