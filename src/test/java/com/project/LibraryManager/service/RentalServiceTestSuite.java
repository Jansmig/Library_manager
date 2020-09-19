package com.project.LibraryManager.service;

import com.project.LibraryManager.domain.Rental;
import com.project.LibraryManager.repository.RentalReposiotry;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RentalServiceTestSuite {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private RentalReposiotry rentalReposiotry;

    @Test
    public void testGetActiveRentals(){
        //given:
        Rental rentalOne = new Rental();
        rentalOne.setActive(true);
        Rental rentalTwo = new Rental();
        rentalTwo.setActive(true);

        rentalService.saveRental(rentalOne);
        rentalService.saveRental(rentalTwo);

        long rentalOneId = rentalOne.getId();
        long rentalTwoId = rentalTwo.getId();

        //when:
        List<Rental> rentals = new ArrayList<>();
        rentals = rentalService.getActiveRentals();

        //then:
        Assert.assertEquals(2, rentals.size());

        //clean-up:
        rentalReposiotry.deleteById(rentalOneId);
        rentalReposiotry.deleteById(rentalTwoId);

    }

}
