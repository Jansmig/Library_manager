package com.project.LibraryManager.mapper;

import com.project.LibraryManager.domain.Book;
import com.project.LibraryManager.domain.BookDto;
import com.project.LibraryManager.domain.BookStatus;
import com.project.LibraryManager.domain.Origin;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BookMapperTestSuite {

    @Test
    public void testMapToBookDto(){
        //given
        Origin origin = new Origin("Ron", "Nor", 2000);
        Book book = new Book(1L, origin, BookStatus.AVAILABLE);
        BookMapper bookMapper = new BookMapper();
        //when
        BookDto bookDto = bookMapper.mapToBookDto(book);
        //then
        assertEquals(book.getId(), bookDto.getId());
        assertEquals(book.getOrigin().getId(), bookDto.getOriginId());
        assertEquals(book.getOrigin().getTitle(), bookDto.getTitle());
        assertEquals(book.getBookStatus(), bookDto.getBookStatus());
    }

    @Test
    public void testMapToBookDtoList() {
        //given
        Origin origin = new Origin("Ron", "Nor", 2000);
        Book bookOne = new Book(1L, origin, BookStatus.AVAILABLE);
        Book bookTwo = new Book(2L, origin, BookStatus.AVAILABLE);
        List<Book> bookList = new ArrayList<>();
        bookList.add(bookOne);
        bookList.add(bookTwo);
        BookMapper bookMapper = new BookMapper();
        //when
        List<BookDto> bookDtoList = bookMapper.mapToBookDtoList(bookList);
        //then
        assertEquals(2, bookDtoList.size());
    }

}
