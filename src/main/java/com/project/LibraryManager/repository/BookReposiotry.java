package com.project.LibraryManager.repository;

import com.project.LibraryManager.domain.Book;
import com.project.LibraryManager.domain.BookStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface BookReposiotry extends CrudRepository<Book, Long> {

    @Override
    Book save(Book book);

    @Override
    Optional<Book> findById(Long bookId);

    long countBooksByBookStatusAndOrigin_TitleIgnoreCase(BookStatus bookStatus, String title);

    List<Book> findByBookStatusAndOrigin_TitleIgnoreCase(BookStatus bookStatus, String title);


}
