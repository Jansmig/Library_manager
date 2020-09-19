package com.project.LibraryManager.client;

import com.project.LibraryManager.domain.GoodreadsDto;
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
    public void testGetRating() {
        //given:
        String isbn = "0345339681"; //the Hobbit
        //when:
        String rating = goodreadsClient.getRating(isbn);
        //then:
        Assert.assertEquals(4, rating.length());
    }


}
