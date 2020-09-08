package com.project.LibraryManager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LibraryException extends RuntimeException {

    public LibraryException(String message) {
        super(message);
    }
}
