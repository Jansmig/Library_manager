package com.project.LibraryManager.exception;

public class UserInvalidNameException extends LibraryException {

    public UserInvalidNameException() {
        super("Invalid user first name and/or last name. Both first and last name need to be at least 3 characters long.");
    }
}
