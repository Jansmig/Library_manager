package com.project.LibraryManager.exception;

public class OriginNotFoundException extends LibraryException {

    public OriginNotFoundException() {
        super("Origin with selected ID does not exist");
    }

}
