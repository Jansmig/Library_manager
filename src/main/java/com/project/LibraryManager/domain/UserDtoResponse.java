package com.project.LibraryManager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserDtoResponse {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime userCreationDate;
    private List<Long> rentalsIds;

}
