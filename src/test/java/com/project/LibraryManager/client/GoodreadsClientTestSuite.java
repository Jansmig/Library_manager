package com.project.LibraryManager.client;

import com.project.LibraryManager.domain.GoodreadsDto;
import com.project.LibraryManager.domain.GoodreadsRatingsRequest;
import com.project.LibraryManager.domain.GoodreadsStatsDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GoodreadsClientTestSuite {

    @Autowired
    private GoodreadsClient goodreadsClient;

    @Test
    public void testGetRating() throws InterruptedException {
        //given:
        String isbn = "0345339681"; //the Hobbit
        //when:
        String rating = goodreadsClient.getSingleRating(isbn);
        //then:
        Assert.assertEquals(4, rating.length());
    }

    @Test
    public void testGetMultipleRatings() {

//        GoodreadsRatingsRequest ratingsRequest = new GoodreadsRatingsRequest();
//        ratingsRequest.setIsbns(new String[]{"1416548947", "0817986626", "0441172717"});
//
//        GoodreadsDto goodreadsDto = goodreadsClient.getMultipleRatings(ratingsRequest);
//
//        for (GoodreadsStatsDto stat : goodreadsDto.getGoodreadsResponse()) {
//            System.out.println(stat.getAverageRating());
//        }
    }


}
