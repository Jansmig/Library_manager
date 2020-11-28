package com.project.LibraryManager.mapper;

import com.project.LibraryManager.domain.*;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class RentalMapperTestSuite {

    @Test
    public void testMapToRentalDtoResponse(){
        //given
        RentalMapper rentalMapper = new RentalMapper();
        Origin origin = new Origin(1L,"Ron", "Nor", 2000, "1234567890", new ArrayList<Book>(), 0);
        Book book = new Book(2L, origin, BookStatus.AVAILABLE);
        origin.getBooks().add(book);
        User user = new User(3L, "Bob", "Smith", "bobs@gmail.com", LocalDateTime.now(), new ArrayList<Rental>());
        Rental rental = new Rental(4L, book, user, LocalDateTime.now(), null, true);
        user.getRentals().add(rental);
        //when
        RentalDtoResponse rentalDtoResponse = rentalMapper.mapToRentalDtoResponse(rental);
        //then
        assertEquals(rental.getId(), rentalDtoResponse.getId());
        assertEquals(rental.getBook().getId(), rentalDtoResponse.getBookId());
        assertEquals(rental.getBook().getOrigin().getTitle(), rentalDtoResponse.getBookTitle());
        assertEquals(rental.getUser().getId(), rentalDtoResponse.getUserId());
        assertEquals(rental.getUser().getFirstName(), rentalDtoResponse.getUserFirstName());
        assertEquals(rental.getUser().getLastName(), rentalDtoResponse.getUserLastName());
        assertEquals(rental.getRentalDate(), rentalDtoResponse.getRentalDate());
        assertEquals(rental.getReturnDate(), rentalDtoResponse.getReturnDate());
        assertEquals(rental.isActive(), rentalDtoResponse.isActive());
    }

}
