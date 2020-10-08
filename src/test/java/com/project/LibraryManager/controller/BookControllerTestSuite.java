package com.project.LibraryManager.controller;

import com.google.gson.Gson;
import com.project.LibraryManager.domain.Book;
import com.project.LibraryManager.domain.BookDto;
import com.project.LibraryManager.domain.Origin;
import com.project.LibraryManager.mapper.BookMapper;
import com.project.LibraryManager.service.BookService;
import com.project.LibraryManager.service.OriginService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
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

import static com.project.LibraryManager.domain.BookStatus.AVAILABLE;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OriginService originService;

    @MockBean
    private BookService bookService;

    @MockBean
    private BookMapper bookMapper;

    @Test
    public void testCreateBook() throws Exception {
        //given
        Origin origin = new Origin(1L, "title", "author", 2000, "1234567890", null);
        //when & then
        when(originService.getOrigin(ArgumentMatchers.anyLong())).thenReturn(Optional.of(origin));

        mockMvc.perform(post("/v1/books/createBook")
                .contentType(MediaType.APPLICATION_JSON)
                .param("originId", "1"))
                .andExpect(status().isOk());

        Mockito.verify(bookService, times(1)).saveBook(any());
    }


    @Test
    public void testGetBook() throws Exception {
        //given
        Book book = new Book();
        BookDto bookDto = new BookDto(2L, 1L, "Adventures", AVAILABLE);
        //when & then
        when(bookService.getBook(anyLong())).thenReturn(Optional.of(book));
        when(bookMapper.mapToBookDto(ArgumentMatchers.any(Book.class))).thenReturn(bookDto);

        mockMvc.perform(get("/v1/books/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.originId", is(1)))
                .andExpect(jsonPath("$.title", is("Adventures")))
                .andExpect(jsonPath("$.bookStatus", is("AVAILABLE")));
    }


    @Test
    public void testSetStatus() throws Exception {
        //when & then
        mockMvc.perform(patch("/v1/books/1/available"))
                .andExpect(status().isOk());

        verify(bookService, times(1)).setBookStatus(1L, "available");
    }


    @Test
    public void testGetBooksByStatusAndTitle() throws Exception {
        //given
        BookDto bookDtoA = new BookDto(2L, 1L, "Adventures", AVAILABLE);
        BookDto bookDtoB = new BookDto(3L, 1L, "Adventures", AVAILABLE);
        List<BookDto> bookDtoList = new ArrayList<>();
        bookDtoList.add(bookDtoA);
        bookDtoList.add(bookDtoB);
        //when & then
        when(bookService.getBooksByStatusAndTitle(anyString(), anyString())).thenReturn(new ArrayList<Book>());
        when(bookMapper.mapToBookDtoList(ArgumentMatchers.anyList())).thenReturn(bookDtoList);

        mockMvc.perform(get("/v1/books/available/Adventures"))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].originId", is(1)))
                .andExpect(jsonPath("$[0].title", is("Adventures")))
                .andExpect(jsonPath("$[0].bookStatus", is("AVAILABLE")))
                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[1].originId", is(1)))
                .andExpect(jsonPath("$[1].title", is("Adventures")))
                .andExpect(jsonPath("$[1].bookStatus", is("AVAILABLE")));
    }


    @Test
    public void testCountBooksWithStatusAndTitle() throws Exception {
        //when & then
        when(bookService.countBooksByStatusAndTitle(anyString(), anyString())).thenReturn(4L);

        mockMvc.perform(get("/v1/books/available/Adventures/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(4)));
    }


    @Test
    public void testUpdateBook() throws Exception {
        //given:
        Book book = new Book();
        book.setId(1);
        Origin origin = new Origin();
        origin.setId(2);
        BookDto bookDto = new BookDto(3L, 2L, "any", AVAILABLE);

        Gson gson = new Gson();
        String requestBody = gson.toJson(bookDto);

        //when:
        when(originService.getOrigin(anyLong())).thenReturn(Optional.of(origin));
        when(bookService.getBook(anyLong())).thenReturn(Optional.of(book));

        //then:
        mockMvc.perform(put("/v1/books/updateBook/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());

        verify(bookService, times(1)).saveBook(book);
    }


    @Test
    public void testDeleteBook() throws Exception {
        //given:
        //when & then:
        mockMvc.perform(delete("/v1/books/1"))
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteBook(1L);
    }


    @Test
    public void testGetBooksList() throws Exception {
        //given:
        BookDto bookDto = new BookDto(1L, 2L, "Adventures", AVAILABLE);
        List<BookDto> bookDtoList = new ArrayList<>();
        bookDtoList.add(bookDto);
        //when:
       // when(bookService.getAllBooks()).thenReturn(new ArrayList<>());
        when(bookMapper.mapToBookDtoList(anyList())).thenReturn(bookDtoList);
        //then:
        mockMvc.perform(get("/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Adventures")));
    }

}


