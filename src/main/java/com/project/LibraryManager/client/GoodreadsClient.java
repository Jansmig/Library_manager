package com.project.LibraryManager.client;

import com.project.LibraryManager.domain.GoodreadsDto;
import com.project.LibraryManager.domain.GoodreadsRatingsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
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


    public URI buildUriForSingleRating(String isbn) {
        URI uri = UriComponentsBuilder.fromHttpUrl(goodreadsReviewsUrl)
                .queryParam("key", goodreadsKey)
                .queryParam("isbns", isbn)
                .build()
                .encode()
                .toUri();
        return uri;
    }


    public String getSingleRating(String isbn) throws InterruptedException {

        GoodreadsDto goodreadsDto = restTemplate.getForObject(buildUriForSingleRating(isbn), GoodreadsDto.class);
        String rating = goodreadsDto.getGoodreadsResponse().get(0).getAverageRating();
        Thread.sleep(1000); //Goodreads API term of service: requests can not be sent more frequent than once per second.
        return rating;
    }

    public GoodreadsDto getMultipleRatings(GoodreadsRatingsRequest request) throws InterruptedException {

        URI uri = UriComponentsBuilder.fromHttpUrl(goodreadsReviewsUrl)
                .queryParam("key", goodreadsKey)
                .build()
                .encode()
                .toUri();
        Thread.sleep(1000); //Goodreads API term of service: requests can not be sent more frequent than once per second.
        return restTemplate.postForObject(uri, request, GoodreadsDto.class);
    }


}
