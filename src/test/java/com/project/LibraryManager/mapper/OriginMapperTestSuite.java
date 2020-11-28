package com.project.LibraryManager.mapper;

import com.project.LibraryManager.domain.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OriginMapperTestSuite {

    @Test
    public void testMapToOriginDtoResponse(){
        //given
        Origin origin = new Origin(1L,"Ron", "Nor", 2000, "1234567890", new ArrayList<Book>(), 0);
        Book book = new Book(2L, origin, BookStatus.AVAILABLE);
        origin.getBooks().add(book);
        OriginMapper originMapper = new OriginMapper();
        //when
        OriginDtoResponse originDtoResponse = originMapper.mapToOriginDtoResponse(origin);
        //then
        assertEquals(origin.getId(), originDtoResponse.getId());
        assertEquals(origin.getTitle(), originDtoResponse.getTitle());
        assertEquals(origin.getAuthor(), originDtoResponse.getAuthor());
        assertEquals(origin.getPublishedYear(), originDtoResponse.getPublishedYear());
        assertEquals(origin.getIsbn(), originDtoResponse.getIsbn());
        assertEquals(origin.getBooks().get(0).getId(), originDtoResponse.getBooksIds().get(0).longValue());
    }

    @Test
    public void testMapToOrigin(){
        //given
        Book bookOne = new Book(1L, null, BookStatus.AVAILABLE);
        Book bookTwo = new Book(2L, null, BookStatus.AVAILABLE);
        List<Book> bookList = new ArrayList<>();
        bookList.add(bookOne);
        bookList.add(bookTwo);

        OriginDtoRequest originDtoRequest = new OriginDtoRequest(3L, "Ron", "Nor", 2000, "1234567890", bookList, 0 );
        OriginMapper originMapper = new OriginMapper();
        //when
        Origin origin = originMapper.mapToOrigin(originDtoRequest);
        //then
        assertEquals(originDtoRequest.getId(), origin.getId());
        assertEquals(originDtoRequest.getTitle(), origin.getTitle());
        assertEquals(originDtoRequest.getAuthor(), origin.getAuthor());
        assertEquals(originDtoRequest.getPublishedYear(), origin.getPublishedYear());
        assertEquals(originDtoRequest.getIsbn(), origin.getIsbn());
        assertEquals(originDtoRequest.getBooks(), origin.getBooks());
    }

    @Test
    public void testMapToOriginDtoResponseList(){
        //given
        Origin originOne = new Origin(1L,"Ron", "Nor", 2000, "1234567890", new ArrayList<Book>(), 0);
        Origin originTwo = new Origin(2L,"Rona", "Nora", 2001, "1234567890", new ArrayList<Book>(), 0);
        List<Origin> originList = new ArrayList<>();
        originList.add(originOne);
        originList.add(originTwo);

        OriginMapper originMapper = new OriginMapper();
        //when
        List<OriginDtoResponse> originDtoResponseList = originMapper.mapToOriginDtoResponseList(originList);
        //then
        assertEquals(originOne.getId(), originDtoResponseList.get(0).getId());
        assertEquals(originOne.getTitle(), originDtoResponseList.get(0).getTitle());
        assertEquals(originOne.getAuthor(), originDtoResponseList.get(0).getAuthor());
        assertEquals(originOne.getPublishedYear(), originDtoResponseList.get(0).getPublishedYear());
        assertEquals(originOne.getIsbn(), originDtoResponseList.get(0).getIsbn());
        assertEquals(originTwo.getId(), originDtoResponseList.get(1).getId());
        assertEquals(originTwo.getTitle(), originDtoResponseList.get(1).getTitle());
        assertEquals(originTwo.getAuthor(), originDtoResponseList.get(1).getAuthor());
        assertEquals(originTwo.getPublishedYear(), originDtoResponseList.get(1).getPublishedYear());
        assertEquals(originTwo.getIsbn(), originDtoResponseList.get(1).getIsbn());
    }


}
