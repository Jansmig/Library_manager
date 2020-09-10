package com.project.LibraryManager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class OriginDtoRequest {

    private long id;
    private String title;
    private String author;
    private int publishedYear;
    private String isbn;
    private List<Book> books;

}
