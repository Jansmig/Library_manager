package com.project.LibraryManager.mapper;

import com.project.LibraryManager.domain.Book;
import com.project.LibraryManager.domain.BookDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public BookDto mapToBookDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getOrigin().getId(),
                book.getOrigin().getTitle(),
                book.getBookStatus()
        );
    }

    public List<BookDto> mapToBookDtoList(List<Book> booklist) {
        return booklist.stream()
                .map(n -> mapToBookDto(n))
                .collect(Collectors.toList());
    }


}
