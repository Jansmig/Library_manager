package com.project.LibraryManager.exception;

public class UserInvalidEmailException extends LibraryException {

    public UserInvalidEmailException(){
        super("Invalid e-mail address.");
    }

}
