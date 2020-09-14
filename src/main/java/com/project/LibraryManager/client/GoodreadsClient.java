package com.project.LibraryManager.client;

import com.project.LibraryManager.domain.GoodreadsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class GoodreadsClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${goodreads.key}")
    private String goodreadsKey;

    @Value("${goodreads.review.counts.url}")
    private String goodreadsReviewsUrl;


    public URI buildUri(String isbn) {
        URI uri = UriComponentsBuilder.fromHttpUrl(goodreadsReviewsUrl)
                .queryParam("key", goodreadsKey)
                .queryParam("isbns", isbn)
                .build()
                .encode()
                .toUri();

        return uri;
    }


    public String getRating(String isbn){

        GoodreadsDto goodreadsDto = restTemplate.getForObject(buildUri(isbn), GoodreadsDto.class);
        String rating = goodreadsDto.getGoodreadsResponse().get(0).getAverageRating();
        return rating;
    }

}
