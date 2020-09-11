package com.project.LibraryManager.service;

import com.project.LibraryManager.domain.Origin;
import com.project.LibraryManager.exception.InvalidTitleException;
import com.project.LibraryManager.repository.OriginReposiotry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OriginService {

    @Autowired
    OriginReposiotry originReposiotry;

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



}
