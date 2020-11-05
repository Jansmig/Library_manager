package com.project.LibraryManager.repository;

import com.project.LibraryManager.domain.Book;
import com.project.LibraryManager.domain.Origin;
import com.project.LibraryManager.domain.Rental;
import com.project.LibraryManager.domain.User;
import com.project.LibraryManager.service.OriginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private static Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private OriginService originService;

    @Autowired
    private BookReposiotry bookReposiotry;

    @Autowired
    private UserReposiotry userReposiotry;

    @Autowired
    private RentalReposiotry rentalReposiotry;


    private void initializeData() throws InterruptedException {

        //ORIGINS:
        Origin hobbit = new Origin();
        hobbit.setAuthor("John Ronald Reuel Tolkien");
        hobbit.setTitle("The Hobbit");
        hobbit.setIsbn("0345339681");
        hobbit.setPublishedYear(1937);
        originService.saveOrigin(hobbit);

        Origin rage = new Origin();
        rage.setAuthor("Stephen King");
        rage.setTitle("Rage");
        rage.setIsbn("1444723537");
        rage.setPublishedYear(2012);
        originService.saveOrigin(rage);

        Origin essenceOfFriedman = new Origin();
        essenceOfFriedman.setAuthor("Milton Friedman");
        essenceOfFriedman.setTitle("The Essence of Friedman");
        essenceOfFriedman.setIsbn("0817986626");
        essenceOfFriedman.setPublishedYear(1987);
        originService.saveOrigin(essenceOfFriedman);

        Origin catcher = new Origin();
        catcher.setAuthor("Jerome David Salinger");
        catcher.setTitle("The Catcher in the Rye");
        catcher.setIsbn("7543321726");
        catcher.setPublishedYear(1951);
        originService.saveOrigin(catcher);

        Origin gone = new Origin();
        gone.setAuthor("Margaret Mitchell");
        gone.setTitle("Gone with the Wind");
        gone.setIsbn("1416548947");
        gone.setPublishedYear(1936);
        originService.saveOrigin(gone);


        //BOOKS:

        //hobbits:
        Book hobbitOne = new Book();
        hobbitOne.setOrigin(hobbit);
        hobbit.addBook(hobbitOne);
        bookReposiotry.save(hobbitOne);

        Book hobbitTwo = new Book();
        hobbitTwo.setOrigin(hobbit);
        hobbit.addBook(hobbitTwo);
        bookReposiotry.save(hobbitTwo);

        Book hobbitThree = new Book();
        hobbitThree.setOrigin(hobbit);
        hobbitThree.setLost();
        hobbit.addBook(hobbitThree);
        bookReposiotry.save(hobbitThree);
        originService.saveOrigin(hobbit);

        //rages:
        Book rageOne = new Book();
        rageOne.setOrigin(rage);
        rage.addBook(rageOne);
        bookReposiotry.save(rageOne);

        Book rageTwo = new Book();
        rageTwo.setOrigin(rage);
        rage.addBook(rageTwo);
        bookReposiotry.save(rageTwo);

        Book rageThree = new Book();
        rageThree.setOrigin(rage);
        rage.addBook(rageThree);
        bookReposiotry.save(rageThree);

        Book rageFour = new Book();
        rageFour.setOrigin(rage);
        rage.addBook(rageFour);
        bookReposiotry.save(rageFour);
        originService.saveOrigin(rage);

        //essences:
        Book essenceOne = new Book();
        essenceOne.setOrigin(essenceOfFriedman);
        essenceOfFriedman.addBook(essenceOne);
        bookReposiotry.save(essenceOne);

        Book essenceTwo = new Book();
        essenceTwo.setOrigin(essenceOfFriedman);
        essenceOfFriedman.addBook(essenceTwo);
        bookReposiotry.save(essenceTwo);
        originService.saveOrigin(essenceOfFriedman);

        //catchers:
        Book catcherOne = new Book();
        catcherOne.setOrigin(catcher);
        catcher.addBook(catcherOne);
        bookReposiotry.save(catcherOne);

        Book catcherTwo = new Book();
        catcherTwo.setOrigin(catcher);
        catcher.addBook(catcherTwo);
        bookReposiotry.save(catcherTwo);

        Book catcherThree = new Book();
        catcherThree.setOrigin(catcher);
        catcherThree.setLost();
        catcher.addBook(catcherThree);
        bookReposiotry.save(catcherThree);
        originService.saveOrigin(catcher);

        //goners:
        Book goneOne = new Book();
        goneOne.setOrigin(gone);
        gone.addBook(goneOne);
        bookReposiotry.save(goneOne);

        Book goneTwo = new Book();
        goneTwo.setOrigin(gone);
        goneTwo.setLost();
        gone.addBook(goneTwo);
        bookReposiotry.save(goneTwo);

        Book goneThree = new Book();
        goneThree.setOrigin(gone);
        gone.addBook(goneThree);
        bookReposiotry.save(goneThree);
        originService.saveOrigin(gone);


        //USERS:
        User abe = new User();
        abe.setFirstName("Abe");
        abe.setLastName("Arrow");
        abe.setEmail(abe.getFirstName()+"."+abe.getLastName()+"@gmail.com");
        userReposiotry.save(abe);

        User bob = new User();
        bob.setFirstName("Bob");
        bob.setLastName("Burnes");
        bob.setEmail(bob.getFirstName()+"."+bob.getLastName()+"@gmail.com");
        userReposiotry.save(bob);

        User cynthia = new User();
        cynthia.setFirstName("Cynthia");
        cynthia.setLastName("Calm");
        cynthia.setEmail(cynthia.getFirstName()+"."+cynthia.getLastName()+"@gmail.com");
        userReposiotry.save(cynthia);

        User doyle = new User();
        doyle.setFirstName("Doyle");
        doyle.setLastName("Donovan");
        doyle.setEmail(doyle.getFirstName()+"."+doyle.getLastName()+"@gmail.com");
        userReposiotry.save(doyle);

        User eve = new User();
        eve.setFirstName("Eve");
        eve.setLastName("Evergreen");
        eve.setEmail(eve.getFirstName()+"."+eve.getLastName()+"@gmail.com");
        userReposiotry.save(eve);


        //RENTALS:
        Rental rentalAbeHobbitOne = new Rental(abe, hobbitOne);
        abe.addRental(rentalAbeHobbitOne);
        userReposiotry.save(abe);
        bookReposiotry.save(hobbitOne);
        rentalReposiotry.save(rentalAbeHobbitOne);

        Rental rentalBobHobbitTwo = new Rental(bob, hobbitTwo);
        bob.addRental(rentalBobHobbitTwo);
        userReposiotry.save(bob);
        bookReposiotry.save(hobbitTwo);
        rentalReposiotry.save(rentalBobHobbitTwo);

        Rental rentalCynthiaRageOne = new Rental(cynthia, rageOne);
        cynthia.addRental(rentalCynthiaRageOne);
        userReposiotry.save(cynthia);
        bookReposiotry.save(rageOne);
        rentalReposiotry.save(rentalCynthiaRageOne);

        Rental rentalEveRageTwo = new Rental(eve, rageTwo);
        eve.addRental(rentalEveRageTwo);
        userReposiotry.save(eve);
        bookReposiotry.save(rageTwo);
        rentalReposiotry.save(rentalEveRageTwo);

        Rental rentalAbeEssenceOne = new Rental(abe, essenceOne);
        abe.addRental(rentalAbeEssenceOne);
        userReposiotry.save(abe);
        bookReposiotry.save(essenceOne);
        rentalReposiotry.save(rentalAbeEssenceOne);

        Rental rentalAbeCatcherOne = new Rental(abe, catcherOne);
        abe.addRental(rentalAbeCatcherOne);
        userReposiotry.save(abe);
        bookReposiotry.save(catcherOne);
        rentalReposiotry.save(rentalAbeCatcherOne);

        Rental rentalDoyleCatcherTwo = new Rental(doyle, catcherTwo);
        doyle.addRental(rentalDoyleCatcherTwo);
        userReposiotry.save(doyle);
        bookReposiotry.save(catcherTwo);
        rentalReposiotry.save(rentalDoyleCatcherTwo);

        Rental rentalEveGoneOne = new Rental(eve, goneOne);
        eve.addRental(rentalEveGoneOne);
        userReposiotry.save(eve);
        bookReposiotry.save(goneOne);
        rentalReposiotry.save(rentalEveGoneOne);

        //CLOSING RENTALS:
        Thread.sleep(1000);

        rentalAbeHobbitOne.returnBook();
        abe.getRentals().remove(0);
        rentalReposiotry.save(rentalAbeHobbitOne);
        bookReposiotry.save(hobbitOne);
        userReposiotry.save(abe);

        rentalEveGoneOne.returnBook();
        eve.getRentals().remove(0);
        rentalReposiotry.save(rentalEveGoneOne);
        bookReposiotry.save(goneOne);
        userReposiotry.save(eve);

    }


    public void run(ApplicationArguments args) throws InterruptedException {
        LOGGER.info("INITIALIZING LIBRARY RESOURCES...");
        initializeData();
        LOGGER.info("RESOURCES INITIALIZED!");

    }

}
