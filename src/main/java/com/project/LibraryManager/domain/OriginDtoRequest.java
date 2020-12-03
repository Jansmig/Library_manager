package com.project.LibraryManager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OriginDtoRequest {

    private long id;
    private String title;
    private String author;
    private int publishedYear;
    private String isbn;
    private List<Book> books;
    private double rating;

}
