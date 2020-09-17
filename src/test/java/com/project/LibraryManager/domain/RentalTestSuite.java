package com.project.LibraryManager.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class RentalTestSuite {

    @Test
    public void testReturnBook() throws InterruptedException {
        //given
        User user = new User("Joe", "Doe");
        Book book = new Book();
        Rental rental = new Rental(user, book);
        Thread.sleep(2);
        //when
        rental.returnBook();
        //then
        assertNotEquals(rental.getRentalDate(), rental.getReturnDate());
        assertFalse(rental.isActive());
        assertEquals(BookStatus.AVAILABLE ,rental.getBook().getBookStatus());
    }

}
