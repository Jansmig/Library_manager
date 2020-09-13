package com.project.LibraryManager.controller;

import com.project.LibraryManager.domain.Book;
import com.project.LibraryManager.domain.BookDto;
import com.project.LibraryManager.exception.BookNotFoundException;
import com.project.LibraryManager.exception.OriginNotFoundException;
import com.project.LibraryManager.exception.StatusNotFoundException;
import com.project.LibraryManager.mapper.BookMapper;
import com.project.LibraryManager.service.BookService;
import com.project.LibraryManager.service.OriginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private OriginService originService;


    @RequestMapping(value = "/books/createBook", method = RequestMethod.POST)
    public void createBook(@RequestParam long originId) throws OriginNotFoundException {
        Book tempBook = new Book();
        tempBook.setOrigin(originService.getOrigin(originId).orElseThrow(OriginNotFoundException::new));
        bookService.saveBook(tempBook);
    }

    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.GET)
    public BookDto getBook(@PathVariable long bookId) throws BookNotFoundException {
        return bookMapper.mapToBookDto(bookService.getBook(bookId).orElseThrow(BookNotFoundException::new));
    }

    @RequestMapping(value = "/books/{bookId}/{bookStatus}", method = RequestMethod.PATCH)
    public void setStatus(@PathVariable long bookId, @PathVariable String bookStatus) throws BookNotFoundException, StatusNotFoundException {
        bookService.setBookStatus(bookId, bookStatus);
    }

    @RequestMapping(value = "/books/{bookStatus}/{title}", method = RequestMethod.GET)
    public List<BookDto> getBooksByStatusAndTitle(@PathVariable String bookStatus, @PathVariable String title) {
        return bookMapper.mapToBookDtoList(bookService.getBooksByStatusAndTitle(bookStatus, title));
    }

    @RequestMapping(value = "/books/{bookStatus}/{title}/count", method = RequestMethod.GET)
    public long countBooksWithStatusAndTitle(@PathVariable String bookStatus, @PathVariable String title) {
        return bookService.countBooksByStatusAndTitle(bookStatus, title);
    }

}
