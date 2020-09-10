package com.project.LibraryManager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class OriginDtoResponse {

    private long id;
    private String title;
    private String author;
    private int publishedYear;
    private int isbn;
    private List<Long> booksIds;

}
