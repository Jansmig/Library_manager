package com.project.LibraryManager.domain;

import com.project.LibraryManager.exception.StatusNotFoundException;
import org.junit.Assert;
import org.junit.Test;

import static com.project.LibraryManager.domain.BookStatus.*;
import static org.mockito.ArgumentMatchers.anyString;

public class BookStatusTestSuite {

    @Test
    public void testConvertStringToStatus() {
        //then
        Assert.assertEquals(AVAILABLE, BookStatus.convertStringToStatus("AvaILaBle"));
        Assert.assertEquals(RENTED, BookStatus.convertStringToStatus("reNTed"));
        Assert.assertEquals(LOST, BookStatus.convertStringToStatus("LosT"));
    }

    @Test(expected = StatusNotFoundException.class)
    public void testConvertStringToStatusException() {
        //when
        BookStatus.convertStringToStatus(anyString());
    }

}
