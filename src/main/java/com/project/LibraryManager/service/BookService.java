package com.project.LibraryManager.service;

import com.project.LibraryManager.domain.Book;
import com.project.LibraryManager.domain.BookStatus;
import com.project.LibraryManager.exception.BookNotFoundException;
import com.project.LibraryManager.exception.StatusNotFoundException;
import com.project.LibraryManager.repository.BookReposiotry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookReposiotry bookReposiotry;

    public Book saveBook(final Book book) {
        return bookReposiotry.save(book);
    }

    public Optional<Book> getBook(Long bookId) {
        return bookReposiotry.findById(bookId);
    }

    public void setBookStatus(Long bookId, String bookStringStatus) throws BookNotFoundException, StatusNotFoundException {
        Book tempBook = bookReposiotry.findById(bookId).orElseThrow(BookNotFoundException::new);
        BookStatus bookStatus = BookStatus.convertStringToStatus(bookStringStatus);
        switch (bookStatus) {
            case AVAILABLE:
                tempBook.setAvailable();
                break;
            case RENTED:
                tempBook.setRented();
                break;
            case LOST:
                tempBook.setLost();
                break;
            default:
                throw new StatusNotFoundException();
        }
        bookReposiotry.save(tempBook);
    }

    public long countBooksByStatusAndTitle(String bookStringStatus, String title) throws StatusNotFoundException {
        BookStatus bookStatus = BookStatus.convertStringToStatus(bookStringStatus);
        return bookReposiotry.countBooksByBookStatusAndOrigin_TitleIgnoreCase(bookStatus, title);
    }

    public List<Book> getBooksByStatusAndTitle(String bookStringStatus, String title) throws StatusNotFoundException {
        BookStatus bookStatus = BookStatus.convertStringToStatus(bookStringStatus);
        return bookReposiotry.findByBookStatusAndOrigin_TitleIgnoreCase(bookStatus, title);
    }

    public void deleteBook(long bookId) {
        bookReposiotry.deleteById(bookId);
    }

    public List<Book> getAllBooks() {
        return bookReposiotry.findAll();
    }

}
