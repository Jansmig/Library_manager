package com.project.LibraryManager.exception;

public class StatusNotFoundException extends LibraryException {

    public StatusNotFoundException(){
        super("Invalid status requested. Please select one from: AVAILABLE, LOST, RENTED.");
    }

}
