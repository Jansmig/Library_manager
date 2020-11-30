package com.project.LibraryManager.service;

import com.project.LibraryManager.client.GoodreadsClient;
import com.project.LibraryManager.domain.*;
import com.project.LibraryManager.exception.InvalidTitleException;
import com.project.LibraryManager.exception.OriginNotFoundException;
import com.project.LibraryManager.repository.OriginReposiotry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OriginService {

    @Autowired
    OriginReposiotry originReposiotry;

    @Autowired
    GoodreadsClient goodreadsClient;

    public Origin saveOrigin(final Origin origin) {
        return originReposiotry.save(origin);
    }

    public Optional<Origin> getOrigin(Long originId) {
        return originReposiotry.findById(originId);
    }

    public List<Origin> getAllOrigins() {
        return originReposiotry.findAll();
    }

    public List<String> findTitlesByPhrase(String phrase) throws InvalidTitleException {
        validateTitleLength(phrase);
        return originReposiotry.findAllByTitleContainingIgnoreCase(phrase).stream()
                .map(n -> n.getTitle())
                .collect(Collectors.toList());
    }

    public void validateTitleLength (String phrase) throws InvalidTitleException {
        if (phrase.length() < 3) {
            throw new InvalidTitleException();
        }
    }

    public void deleteOrigin(long originId) {
        originReposiotry.deleteById(originId);
    }

    public void updateOriginRating(OriginDtoRequest originDtoRequest) throws InterruptedException {
        long originId = originDtoRequest.getId();
        Origin updatedOrigin = getOrigin(originId).orElseThrow(OriginNotFoundException::new);
        String isbn = originDtoRequest.getIsbn();
        String ratingString = goodreadsClient.getSingleRating(isbn);
        double rating = 0;
        if(ratingString != null) {
            rating = Double.parseDouble(ratingString);
        }
        updatedOrigin.setRating(rating);
        saveOrigin(updatedOrigin);
    }

    public void updateAllOriginsRatings() throws InterruptedException {
        List<Origin> originsList = getAllOrigins();
        filterValidIsbns(originsList);
        GoodreadsRatingsRequest goodreadsRatingsRequest = createGoodreadsRatingsRequest(originsList);
        GoodreadsDto goodreadsResponse = goodreadsClient.getMultipleRatings(goodreadsRatingsRequest);
        Map<String, Double> isbnsRatingsMap = createRatingsMap(goodreadsResponse);
        for (Origin o : originsList) {
            o.setRating(isbnsRatingsMap.get(o.getIsbn()));
            saveOrigin(o);
        }
    }

    private Map<String, Double> createRatingsMap(GoodreadsDto goodreadsDto) {
        Map<String, Double> isbnsRatingsMap = new HashMap<>();
        for (GoodreadsStatsDto stat : goodreadsDto.getGoodreadsResponse()) {
            isbnsRatingsMap.put(stat.getIsbn(), Double.parseDouble(stat.getAverageRating()));
            isbnsRatingsMap.put(stat.getIsbn13(), Double.parseDouble(stat.getAverageRating()));
        }
        return isbnsRatingsMap;
    }

    private GoodreadsRatingsRequest createGoodreadsRatingsRequest(List<Origin> originsList) {
        String[] isbnsArray = new String[originsList.size()];
        for(int i = 0; i < originsList.size(); i++) {
            isbnsArray[i] = originsList.get(i).getIsbn();
        }
        return new GoodreadsRatingsRequest(isbnsArray);
    }

    private void filterValidIsbns(List<Origin> originsList) {
        originsList.stream()
                .filter(o -> o.getIsbn().matches("[\\d]{10}|[\\d]{13}"))
                .collect(Collectors.toList());
    }


}
