package com.project.LibraryManager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookDto {

    private long id;

    private long originId;

    private String title;

    private BookStatus bookStatus;

}
