package com.project.LibraryManager.exception;

public class InvalidIsbnInputException extends LibraryException {

    public InvalidIsbnInputException(){
        super("Invalid ISBN input. ISBN has to be a 10 or 13 characters long Integer");
    }

}
