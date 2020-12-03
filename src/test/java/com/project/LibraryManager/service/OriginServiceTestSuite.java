package com.project.LibraryManager.service;

import com.project.LibraryManager.client.GoodreadsClient;
import com.project.LibraryManager.domain.Origin;
import com.project.LibraryManager.domain.OriginDtoRequest;
import com.project.LibraryManager.exception.*;
import com.project.LibraryManager.repository.DataLoader;
import com.project.LibraryManager.repository.OriginReposiotry;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OriginServiceTestSuite {

    @Autowired
    private OriginReposiotry originReposiotry;

    @Autowired
    private OriginService originService;


    @Test
    public void testFindTitlesByPhrase() {
        //given
        Origin originA = new Origin("Ron", "Adventures", 2000);
        Origin originB = new Origin("Pom", "Treasures", 2001);
        //when
        originService.saveOrigin(originA);
        originService.saveOrigin(originB);
        long originAId = originA.getId();
        long originBId = originB.getId();

        List<String> resultList = originService.findTitlesByPhrase("uREs");
        //then
        assertTrue(resultList.contains("Adventures"));
        assertTrue(resultList.contains("Treasures"));

        //clean up
        originReposiotry.deleteById(originAId);
        originReposiotry.deleteById(originBId);
    }

    @Test(expected = InvalidTitleException.class)
    public void testValidateInvalidTitleLength() {
        originService.validateTitleLength("aa");
    }

    @Test
    public void testValidateValidTitleLength() {
        originService.validateTitleLength("aaA");
    }

    @Test
    public void testValidateInvalidYearInput() {
        //when & then:
        Assertions.assertThrows(InvalidYearInputException.class, () -> originService.validateYearInput(null));
        Assertions.assertThrows(InvalidYearInputException.class, () -> originService.validateYearInput(-4444));
        Assertions.assertThrows(InvalidYearInputException.class, () -> originService.validateYearInput(3333));
    }

    @Test
    public void testValidateValidYearInput() {
        //when & then:
        originService.validateYearInput(0);
        originService.validateYearInput(-4000);
        originService.validateYearInput(-3000);
        originService.validateYearInput(-100);
        originService.validateYearInput(-2020);
        originService.validateYearInput(LocalDateTime.now().getYear());
    }

    @Test
    public void testValidateInvalidIsbnInput() {
        //when & then:
        Assertions.assertThrows(InvalidIsbnInputException.class, () -> originService.validateIsbnInput(null));
        Assertions.assertThrows(InvalidIsbnInputException.class, () -> originService.validateIsbnInput("123"));
        Assertions.assertThrows(InvalidIsbnInputException.class, () -> originService.validateIsbnInput("123w123123"));
        Assertions.assertThrows(InvalidIsbnInputException.class, () -> originService.validateIsbnInput("111g222333444"));
        Assertions.assertThrows(InvalidIsbnInputException.class, () -> originService.validateIsbnInput("3rdfesww4"));
    }

    @Test
    public void testValidateValidIsbnInput() {
        //when & then:
        originService.validateIsbnInput("1112223334");
        originService.validateIsbnInput("1112223334445");
    }

    @Test
    public void testValidateIfExistingIsbnAlreadyExists() {
        //given:
        Origin origin = new Origin();
        origin.setAuthor("Rob");
        origin.setTitle("Bor");
        origin.setIsbn("0123456789");
        originService.saveOrigin(origin);
        long originId = origin.getId();
        //when & then:
        Assertions.assertThrows(IsbnAlreadyExistsException.class, () -> originService.validateIfIsbnAlreadyExists("0123456789"));
        //clean-up:
        originService.deleteOrigin(originId);
    }

    @Test
    public void testValidateIfNonexistingIsbnAlreadyExists() {
        //when & then:
        originService.validateIfIsbnAlreadyExists("0000000000");
    }

    @Test
    public void testUpdateOriginRating() throws InterruptedException {
        //given:
        Origin origin = new Origin();
        origin.setAuthor("J.R.R. Tolkien");
        origin.setTitle("Hobbit");
        origin.setIsbn("0345339681");
        originService.saveOrigin(origin);
        long originId = origin.getId();

        OriginDtoRequest originDtoRequest = new OriginDtoRequest();
        originDtoRequest.setId(originId);
        originDtoRequest.setIsbn(origin.getIsbn());
        //when:
        originService.updateOriginRating(originDtoRequest);
        //then:
        Origin updatedOrigin = originService.getOrigin(originId).orElseThrow(OriginNotFoundException::new);
        Assert.assertNotEquals(0.0, updatedOrigin.getRating());
        //clean-up:
        originService.deleteOrigin(originId);
    }

    @Test
    public void testUpdateAllOriginsRatings() throws InterruptedException {
        //given:
        Origin dune = new Origin();
        dune.setAuthor("Frank Herbert");
        dune.setTitle("Dune");
        dune.setIsbn("0441172717");
        originService.saveOrigin(dune);
        long duneId = dune.getId();

        Origin milczenie = new Origin();
        milczenie.setAuthor("Thomas Harris");
        milczenie.setTitle("Milczenie owiec");
        milczenie.setIsbn("9788379856930");
        originService.saveOrigin(milczenie);
        long milczenieId = milczenie.getId();
        //when:
        originService.updateAllOriginsRatings();
        //then:
        Origin updatedDune = originService.getOrigin(duneId).orElseThrow(OriginNotFoundException::new);
        Origin updatedMilczenie = originService.getOrigin(milczenieId).orElseThrow(OriginNotFoundException::new);
        Assert.assertNotEquals(0.0, updatedDune.getRating());
        Assert.assertNotEquals(0.0, updatedMilczenie.getRating());
        //clean-up:
        originService.deleteOrigin(duneId);
        originService.deleteOrigin(milczenieId);
    }


}