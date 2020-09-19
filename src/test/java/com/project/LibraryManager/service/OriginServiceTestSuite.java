package com.project.LibraryManager.service;

import com.project.LibraryManager.domain.Origin;
import com.project.LibraryManager.exception.InvalidTitleException;
import com.project.LibraryManager.repository.OriginReposiotry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void testFindTitlesByPhrase(){
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
    public void testValidateInvalidTitleLength(){
        originService.validateTitleLength("aa");
    }

    @Test
    public void testValidateValidTitleLength(){
        originService.validateTitleLength("aaA");

    }

}

