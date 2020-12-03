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
        String hobbitIsbn = "0345339681";
        //when:
        String rating = goodreadsClient.getSingleRating(hobbitIsbn);
        //then:
        Assert.assertEquals(4, rating.length());
        Assert.assertNotEquals(0.0, Double.parseDouble(rating));
        Assert.assertNotNull(rating);
    }

    @Test
    public void testGetMultipleRatings() throws InterruptedException {
        //given:
        String goneWithTheWindIsbn = "1416548947";
        String essenceOfFriedmanIsbn = "0817986626";
        String duneIsbn = "0441172717";
        GoodreadsRatingsRequest ratingsRequest = new GoodreadsRatingsRequest();
        ratingsRequest.setIsbns(new String[]{goneWithTheWindIsbn, essenceOfFriedmanIsbn, duneIsbn});
        //when:
        GoodreadsDto goodreadsDto = goodreadsClient.getMultipleRatings(ratingsRequest);
        //then:
        Assert.assertEquals(4, goodreadsDto.getGoodreadsResponse().get(0).getAverageRating().length());
        Assert.assertEquals(4, goodreadsDto.getGoodreadsResponse().get(1).getAverageRating().length());
        Assert.assertEquals(4, goodreadsDto.getGoodreadsResponse().get(2).getAverageRating().length());
        Assert.assertNotEquals(0.0, Double.parseDouble(goodreadsDto.getGoodreadsResponse().get(0).getAverageRating()));
        Assert.assertNotEquals(0.0, Double.parseDouble(goodreadsDto.getGoodreadsResponse().get(1).getAverageRating()));
        Assert.assertNotEquals(0.0, Double.parseDouble(goodreadsDto.getGoodreadsResponse().get(2).getAverageRating()));
        Assert.assertNotNull(goodreadsDto.getGoodreadsResponse().get(0).getAverageRating());
        Assert.assertNotNull(goodreadsDto.getGoodreadsResponse().get(1).getAverageRating());
        Assert.assertNotNull(goodreadsDto.getGoodreadsResponse().get(2).getAverageRating());
    }


}
