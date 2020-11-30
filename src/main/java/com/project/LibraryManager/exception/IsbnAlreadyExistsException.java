package com.project.LibraryManager.exception;

public class IsbnAlreadyExistsException  extends LibraryException{

    public IsbnAlreadyExistsException(){
        super("Origin with this ISBN already exists in the database.");
    }

}
