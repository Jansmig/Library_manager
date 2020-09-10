package com.project.LibraryManager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookDto {

    private long id;

    private long originId;

    private String title;

    private BookStatus bookStatus;

}
