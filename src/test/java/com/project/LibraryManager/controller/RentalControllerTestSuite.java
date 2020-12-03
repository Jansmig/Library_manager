package com.project.LibraryManager.controller;

import com.google.gson.Gson;
import com.project.LibraryManager.domain.*;
import com.project.LibraryManager.mapper.RentalMapper;
import com.project.LibraryManager.service.BookService;
import com.project.LibraryManager.service.RentalService;
import com.project.LibraryManager.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RentalController.class)
public class RentalControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalService rentalService;

    @MockBean
    private RentalMapper rentalMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private BookService bookService;


    @Test
    public void testCreateRental() throws Exception {
        //given
        User user = new User("Rob", "Tester");
        user.setId(3L);
        Book book = new Book();
        book.setAvailable();
        book.setId(2L);
        RentalDtoRequest rentalDtoRequest = new RentalDtoRequest(3L, 2L);

        Gson gson = new Gson();
        String requestBody = gson.toJson(rentalDtoRequest);

        //when & then
        Mockito.when(userService.getUser(ArgumentMatchers.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(bookService.getBook(ArgumentMatchers.anyLong())).thenReturn(Optional.of(book));

        mockMvc.perform(post("/v1/rentals/createRental")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());

        Mockito.verify(rentalService, times(1)).saveRental(ArgumentMatchers.any(Rental.class));
    }


    @Test
    public void testCloseRental() throws Exception {
        //given
        User user = new User("Rob", "Tester");
        user.setId(3L);
        Book book = new Book();
        book.setAvailable();
        book.setId(2L);
        Rental rental = new Rental(user, book);
        rental.setId(4L);

        //when & then
        Mockito.when(rentalService.getRental(ArgumentMatchers.anyLong())).thenReturn(Optional.of(rental));

        mockMvc.perform(put("/v1/rentals/4"))
                .andExpect(status().isOk());

        Mockito.verify(rentalService, times(1)).saveRental(rental);
        Assert.assertFalse(rental.isActive());
    }


    @Test
    public void testGetRental() throws Exception {
        //given
        User user = new User("Rob", "Tester");
        user.setId(3L);
        Book book = new Book();
        book.setAvailable();
        book.setId(2L);
        Rental rental = new Rental(user, book);
        rental.setId(4L);

        RentalDtoResponse rentalDtoResponse = new RentalDtoResponse(
                4L,
                2L,
                "Adventures",
                3L,
                "Rob",
                "Tester",
                rental.getRentalDate(),
                rental.getReturnDate(),
                rental.isActive());

        //when & then
        Mockito.when(rentalService.getRental(ArgumentMatchers.anyLong())).thenReturn(Optional.of(rental));
        Mockito.when(rentalMapper.mapToRentalDtoResponse(ArgumentMatchers.any(Rental.class))).thenReturn(rentalDtoResponse);

        mockMvc.perform(get("/v1/rentals/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.bookId", is(2)))
                .andExpect(jsonPath("$.bookTitle", is("Adventures")))
                .andExpect(jsonPath("$.userId", is(3)))
                .andExpect(jsonPath("$.userFirstName", is("Rob")))
                .andExpect(jsonPath("$.userLastName", is("Tester")))
             //   .andExpect(jsonPath("$.rentalDate", is(String.valueOf(rental.getRentalDate()))))
                .andExpect(jsonPath("$.active", is(true)));

        Mockito.verify(rentalMapper, times(1)).mapToRentalDtoResponse(ArgumentMatchers.any(Rental.class));
    }


    @Test
    public void testUpdateRental() throws Exception {
        //given:
        User userA = new User();
        Book bookA = new Book();
        Rental rental = new Rental(userA, bookA);
        rental.setId(10);

        User userB = new User();
        userB.setFirstName("Bob");
        userB.setId(20);

        Book bookB = new Book();
        bookB.setAvailable();
        bookB.setId(21);

        RentalDtoRequest rentalDtoRequest = new RentalDtoRequest(20, 21);

        Gson gson = new Gson();
        String requestBody = gson.toJson(rentalDtoRequest);

        //when:
        when(rentalService.getRental(anyLong())).thenReturn(Optional.of(rental));
        when(userService.getUser(anyLong())).thenReturn(Optional.of(userB));
        when(bookService.getBook(anyLong())).thenReturn(Optional.of(bookB));

        //then:
        mockMvc.perform(patch("/v1/rentals/updateRental/10")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(requestBody))
                .andExpect(status().isOk());

        Mockito.verify(rentalService, times(1)).saveRental(rental);
    }


    @Test
    public void testGetAllRentals() throws Exception {
        //given:
        RentalDtoResponse rentalDtoResponse = new RentalDtoResponse(
                4L,
                2L,
                "Adventures",
                3L,
                "Rob",
                "Tester",
                null,
                null,
                true
        );

        List<RentalDtoResponse> rentalDtoResponseList = new ArrayList<>();
        rentalDtoResponseList.add(rentalDtoResponse);
        //when:
        when(rentalMapper.mapToRentalDtoResponseList(anyList())).thenReturn(rentalDtoResponseList);
        //then:
        mockMvc.perform(get("/v1/rentals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(4)))
                .andExpect(jsonPath("$[0].userFirstName", is("Rob")));

    }

    @Test
    public void testDeleteRental() throws Exception {
        //given:
        //when & then:
        mockMvc.perform(delete("/v1/rentals/1"))
                .andExpect(status().isOk());

        Mockito.verify(rentalService, times(1)).deleteRental(1L);
    }


}
