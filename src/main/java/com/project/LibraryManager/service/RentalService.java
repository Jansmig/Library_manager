package com.project.LibraryManager.service;

import com.project.LibraryManager.domain.Rental;
import com.project.LibraryManager.repository.RentalReposiotry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    @Autowired
    RentalReposiotry rentalReposiotry;

    public Rental saveRental(final Rental rental) {
        return rentalReposiotry.save(rental);
    }

    public Optional<Rental> getRental(Long rentalId) {
        return rentalReposiotry.findById(rentalId);
    }

    public List<Rental> getActiveRentals() {
        return rentalReposiotry.findAllByActiveIsTrue();
    }

    public List<Rental> getAllRentals(){
        return rentalReposiotry.findAll();
    }

    public void deleteRental(long rentalId){
        rentalReposiotry.deleteById(rentalId);
    }

}
