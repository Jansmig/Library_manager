package com.project.LibraryManager.controller;

import com.project.LibraryManager.domain.Book;
import com.project.LibraryManager.domain.BookDto;
import com.project.LibraryManager.domain.Origin;
import com.project.LibraryManager.exception.BookNotFoundException;
import com.project.LibraryManager.exception.OriginNotFoundException;
import com.project.LibraryManager.exception.StatusNotFoundException;
import com.project.LibraryManager.mapper.BookMapper;
import com.project.LibraryManager.service.BookService;
import com.project.LibraryManager.service.OriginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

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
        Origin baseOrigin = originService.getOrigin(originId).orElseThrow(OriginNotFoundException::new);
        baseOrigin.addBook(tempBook);
        originService.saveOrigin(baseOrigin);
    }

    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.GET)
    public BookDto getBook(@PathVariable long bookId) throws BookNotFoundException {
        return bookMapper.mapToBookDto(bookService.getBook(bookId).orElseThrow(BookNotFoundException::new));
    }

    @RequestMapping(value = "/books/{bookStatus}/{title}", method = RequestMethod.GET)
    public List<BookDto> getBooksByStatusAndTitle(@PathVariable String bookStatus, @PathVariable String title) {
        return bookMapper.mapToBookDtoList(bookService.getBooksByStatusAndTitle(bookStatus, title));
    }

    @RequestMapping(value = "/books/{bookStatus}/{title}/count", method = RequestMethod.GET)
    public long countBooksWithStatusAndTitle(@PathVariable String bookStatus, @PathVariable String title) {
        return bookService.countBooksByStatusAndTitle(bookStatus, title);
    }

    @RequestMapping(value = "/books/updateBook/{bookId}", method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    public void updateBook(@RequestBody BookDto bookDto, @PathVariable long bookId) throws OriginNotFoundException, BookNotFoundException {
        Origin tempOrigin = originService.getOrigin(bookDto.getOriginId()).orElseThrow(OriginNotFoundException::new);
        Book updatedBook = bookService.getBook(bookId).orElseThrow(BookNotFoundException::new);
        updatedBook.setOrigin(tempOrigin);
        bookService.saveBook(updatedBook);
    }

    @RequestMapping(value = "/books/setBookStatus/{bookId}/{bookStatus}", method = RequestMethod.PUT)
    public void setStatusPut(@PathVariable long bookId, @PathVariable String bookStatus) throws BookNotFoundException, StatusNotFoundException {
        bookService.setBookStatus(bookId, bookStatus);
    }

    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.DELETE)
    public void deleteBook(@PathVariable long bookId) {
        try {
            bookService.deleteBook(bookId);
        } catch (Exception e) {
            throw new BookNotFoundException();
        }
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public List<BookDto> getBooksList() {
       return bookMapper.mapToBookDtoList(bookService.getAllBooks());
    }

}
