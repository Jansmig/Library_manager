package com.project.LibraryManager.exception;

public class UserNotFoundException extends LibraryException {

    public UserNotFoundException() {
        super("User with selected ID does not exist.");
    }
}
