package com.project.LibraryManager.exception;

public class IsbnNotFoundException extends LibraryException{

    public IsbnNotFoundException(){
        super("Invalid or unknown ISBN.");
    }
}
