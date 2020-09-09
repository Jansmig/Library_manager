package com.project.LibraryManager.repository;

import com.project.LibraryManager.domain.Rental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface RentalReposiotry extends CrudRepository<Rental, Long> {

    @Override
    Rental save(Rental rental);

    @Override
    Optional<Rental> findById(Long rentalId);

}
