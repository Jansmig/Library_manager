package com.project.LibraryManager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OriginDtoResponse {

    private long id;
    private String title;
    private String author;
    private int publishedYear;
    private String isbn;
    private List<Long> booksIds;
    private double rating;

}
