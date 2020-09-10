package com.project.LibraryManager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RentalDtoRequest {

    private long userId;
    private long bookId;

}
