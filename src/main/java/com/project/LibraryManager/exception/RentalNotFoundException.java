package com.project.LibraryManager.exception;

public class RentalNotFoundException  extends  LibraryException{

    public RentalNotFoundException(){
        super("Rental with selected ID does not exist.");
    }

}
