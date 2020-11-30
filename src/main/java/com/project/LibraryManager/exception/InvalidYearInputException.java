package com.project.LibraryManager.exception;

public class InvalidYearInputException extends LibraryException {

    public InvalidYearInputException(){
        super("Invalid year of publication.");
    }
}
