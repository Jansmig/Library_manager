package com.project.LibraryManager.service;

import com.project.LibraryManager.domain.Book;
import com.project.LibraryManager.domain.BookStatus;
import com.project.LibraryManager.domain.Origin;
import com.project.LibraryManager.exception.BookNotAvailableException;
import com.project.LibraryManager.exception.StatusNotFoundException;
import com.project.LibraryManager.repository.BookReposiotry;
import com.project.LibraryManager.repository.OriginReposiotry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTestSuite {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookReposiotry bookReposiotry;

    @Autowired
    private OriginReposiotry originReposiotry;

    private static final BookStatus AVAILABLE = BookStatus.AVAILABLE;
    private static final BookStatus RENTED = BookStatus.RENTED;
    private static final BookStatus LOST = BookStatus.LOST;

    @Test
    public void testSaveBook() {
        //given
        Book book = new Book();
        book.setAvailable();
        //when
        bookService.saveBook(book);
        long bookId = book.getId();
        //then
        assertTrue(bookReposiotry.existsById(bookId));
        //clean up
        bookReposiotry.deleteById(bookId);
    }

    @Test
    public void testGetBook() {
        //given
        Book book = new Book();
        book.setAvailable();
        //when
        bookService.saveBook(book);
        long bookId = book.getId();
        Book fetchedBook = bookService.getBook(bookId).orElse(null);
        boolean isAvailable = fetchedBook.getBookStatus() == AVAILABLE;
        //then
        assertTrue(isAvailable);
        //clean up
        bookReposiotry.deleteById(bookId);
    }

    @Test
    public void testSetBookAvailableStatus(){
        //given
        Book book = new Book();
        //when
        bookService.saveBook(book);
        long bookId = book.getId();
        bookService.setBookStatus(bookId, "available");
        Book fetchedBook = bookService.getBook(bookId).orElse(null);
        //then
        assert fetchedBook != null;
        assertEquals(AVAILABLE, fetchedBook.getBookStatus());
        //clean up
        bookReposiotry.deleteById(bookId);
    }

    @Test
    public void testSetBookRentedStatus(){
        //given
        Book book = new Book();
        //when
        bookService.saveBook(book);
        long bookId = book.getId();
        bookService.setBookStatus(bookId, "RENteD");
        Book fetchedBook = bookService.getBook(bookId).orElse(null);
        //then
        assert fetchedBook != null;
        assertEquals(RENTED, fetchedBook.getBookStatus());
        //clean up
        bookReposiotry.deleteById(bookId);
    }

    @Test
    public void testSetBookLostStatus(){
        //given
        Book book = new Book();
        //when
        bookService.saveBook(book);
        long bookId = book.getId();
        bookService.setBookStatus(bookId, "LOst");
        Book fetchedBook = bookService.getBook(bookId).orElse(null);
        //then
        assert fetchedBook != null;
        assertEquals(LOST, fetchedBook.getBookStatus());
        //clean up
        bookReposiotry.deleteById(bookId);
    }

    @Test (expected = StatusNotFoundException.class)
    public void testSetBookErrorStatus(){
        //given
        Book book = new Book();
        //when & then
        bookService.saveBook(book);
        long bookId = book.getId();
        bookService.setBookStatus(bookId, "refewfew");
        //clean up
        bookReposiotry.deleteById(bookId);

    }

    @Test
    public void testCountBooksByStatusAndTitle(){
        //given
        Origin origin = new Origin("Ron", "Adventures", 2000);
        Book book = new Book();
        book.setAvailable();
        book.setOrigin(origin);
        //when
        originReposiotry.save(origin);
        bookService.saveBook(book);
        long originId = origin.getId();
        long bookId = book.getId();
        long result = bookService.countBooksByStatusAndTitle("available", "Adventures");
        //then
        assertEquals(1, result);
        //clean up
        bookReposiotry.deleteById(bookId);
        originReposiotry.deleteById(originId);
    }

    @Test
    public void testGetBooksByStatusAndTitle(){
        //given
        Origin origin = new Origin("Ron", "Adventures", 2000);
        Book book = new Book();
        book.setAvailable();
        book.setOrigin(origin);
        //when
        originReposiotry.save(origin);
        bookService.saveBook(book);
        long originId = origin.getId();
        long bookId = book.getId();
        List<Book> resultList = bookService.getBooksByStatusAndTitle("AvaiLABle", "Adventures");
        //then
        assertEquals("Adventures", resultList.get(0).getOrigin().getTitle());
        //clean up
        bookReposiotry.deleteById(bookId);
        originReposiotry.deleteById(originId);
    }

    @Test(expected = BookNotAvailableException.class)
    public void testValidateLostBookAvailability(){
        Book book = new Book();
        book.setLost();
        bookService.validateBookAvailability(book);
    }

    @Test(expected = BookNotAvailableException.class)
    public void testValidateRentedBookAvailability(){
        Book book = new Book();
        book.setRented();
        bookService.validateBookAvailability(book);
    }

    @Test
    public void testValidateAvailableBookAvailability(){
        Book book = new Book();
        book.setAvailable();
        bookService.validateBookAvailability(book);
    }




}
