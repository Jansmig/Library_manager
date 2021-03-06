package com.project.LibraryManager.controller;

import com.project.LibraryManager.client.GoodreadsClient;
import com.project.LibraryManager.domain.OriginDtoRequest;
import com.project.LibraryManager.domain.OriginDtoResponse;
import com.project.LibraryManager.exception.InvalidTitleException;
import com.project.LibraryManager.exception.IsbnNotFoundException;
import com.project.LibraryManager.exception.OriginNotFoundException;
import com.project.LibraryManager.mapper.OriginMapper;
import com.project.LibraryManager.service.OriginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin(origins = "*")
public class OriginController {

    @Autowired
    private OriginService originService;

    @Autowired
    private OriginMapper originMapper;

    @Autowired
    private GoodreadsClient goodreadsClient;

    @RequestMapping(value = "/origins", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public void createOrigin(@RequestBody OriginDtoRequest originDtoRequest) {
        originService.validateYearInput(originDtoRequest.getPublishedYear());
        originService.validateIsbnInput(originDtoRequest.getIsbn());
        originService.validateIfIsbnAlreadyExists(originDtoRequest.getIsbn());
        originService.saveOrigin(originMapper.mapToOrigin(originDtoRequest));
    }

    @RequestMapping(value = "/origins/{originId}", method = RequestMethod.GET)
    public OriginDtoResponse getOrigin(@PathVariable long originId) throws OriginNotFoundException {
        return originMapper.mapToOriginDtoResponse(originService.getOrigin(originId).orElseThrow(OriginNotFoundException::new));
    }

    @RequestMapping(value = "/origins", method = RequestMethod.GET)
    public List<OriginDtoResponse> getOriginsList() {
        return originMapper.mapToOriginDtoResponseList(originService.getAllOrigins());
    }

    @RequestMapping(value = "/origins/{phrase}/search", method = RequestMethod.GET)
    public List<String> getTitlesContainingPhrase(@PathVariable String phrase) throws InvalidTitleException {
        return originService.findTitlesByPhrase(phrase);
    }

    @RequestMapping(value = "/origins/rating/{isbn}", method = RequestMethod.GET)
    public String getOriginRating(@PathVariable String isbn) {
        try{
            return goodreadsClient.getSingleRating(isbn);
        }
        catch (Exception e) {
            String message = "Invalid or unknown ISBN.";
            return message;
        }
    }

    @RequestMapping(value = "/origins/rating", method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    public void updateOriginRating(@RequestBody OriginDtoRequest originDtoRequest) {
        try{
            originService.updateOriginRating(originDtoRequest);
        }
        catch (Exception e) {
            throw new IsbnNotFoundException();
        }
    }

    @RequestMapping(value = "/origins", method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    public void updateOrigin(@RequestBody OriginDtoRequest originDtoRequest) {
        originService.validateYearInput(originDtoRequest.getPublishedYear());
        originService.validateIsbnInput(originDtoRequest.getIsbn());
        originService.saveOrigin(originMapper.mapToOrigin(originDtoRequest));
    }

    @RequestMapping(value = "/origins/{originId}", method = RequestMethod.DELETE)
    public void deleteOrigin(@PathVariable long originId) {
        originService.deleteOrigin(originId);
    }

    @RequestMapping(value = "/origins/allRatings", method = RequestMethod.PUT)
    public void updateAllRatings() throws InterruptedException {
        originService.updateAllOriginsRatings();
    }

}
