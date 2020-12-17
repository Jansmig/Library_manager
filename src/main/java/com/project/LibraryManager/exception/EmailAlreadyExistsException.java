package com.project.LibraryManager.exception;

public class EmailAlreadyExistsException extends LibraryException {

    public EmailAlreadyExistsException(){
        super("Provided e-mail address already exists in the database. Please use other e-mail address.");
    }
}
