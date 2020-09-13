package com.project.LibraryManager.controller;

import com.project.LibraryManager.domain.*;
import com.project.LibraryManager.exception.BookNotFoundException;
import com.project.LibraryManager.exception.RentalNotFoundException;
import com.project.LibraryManager.exception.UserNotFoundException;
import com.project.LibraryManager.mapper.RentalMapper;
import com.project.LibraryManager.service.BookService;
import com.project.LibraryManager.service.RentalService;
import com.project.LibraryManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin(origins = "*")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private RentalMapper rentalMapper;


    @RequestMapping(value = "/rentals/createRental", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public void createRental(@RequestBody RentalDtoRequest rentalDtoRequest) throws UserNotFoundException, BookNotFoundException {
        User tempUser = userService.getUser(rentalDtoRequest.getUserId()).orElseThrow(UserNotFoundException::new);
        Book tempBook = bookService.getBook(rentalDtoRequest.getBookId()).orElseThrow(BookNotFoundException::new);
        Rental rental = new Rental(tempUser, tempBook);
        rentalService.saveRental(rental);
    }

    @RequestMapping(value = "/rentals/{rentalId}", method = RequestMethod.PUT)
    public void closeRental(@PathVariable long rentalId) throws RentalNotFoundException {
        Rental tempRental = rentalService.getRental(rentalId).orElseThrow(RentalNotFoundException::new);
        tempRental.returnBook();
        rentalService.saveRental(tempRental);
    }

    @RequestMapping(value = "/rentals/{rentalId}", method = RequestMethod.GET)
    public RentalDtoResponse getRental(@PathVariable long rentalId) throws RentalNotFoundException {
        return rentalMapper.mapToRentalDtoResponse((rentalService.getRental(rentalId)).orElseThrow(RentalNotFoundException::new));
    }


}