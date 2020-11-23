package com.project.LibraryManager.exception;

public class RentalNotActiveException extends LibraryException {

    public RentalNotActiveException(){
        super("Rental is inactive.");
    }
}
