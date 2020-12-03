package com.project.LibraryManager.controller;

import com.google.gson.Gson;
import com.project.LibraryManager.client.GoodreadsClient;
import com.project.LibraryManager.domain.Origin;
import com.project.LibraryManager.domain.OriginDtoRequest;
import com.project.LibraryManager.domain.OriginDtoResponse;
import com.project.LibraryManager.mapper.OriginMapper;
import com.project.LibraryManager.service.OriginService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OriginController.class)
public class OriginControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OriginMapper originMapper;

    @MockBean
    private OriginService originService;

    @MockBean
    private GoodreadsClient goodreadsClient;

    @Test
    public void testCreateOrigin() throws Exception {
        //given
        OriginDtoRequest originDtoRequest = new OriginDtoRequest(1L, "title", "author", 2000, "1234567890", null, 0);
        Origin origin = new Origin(1L, "title", "author", 2000, "1234567890", null, 0);
        Gson gson = new Gson();
        String requestBody = gson.toJson(originDtoRequest);

        //when & then
        when(originService.saveOrigin(ArgumentMatchers.any(Origin.class))).thenReturn(origin);
        when(originMapper.mapToOrigin(ArgumentMatchers.any(OriginDtoRequest.class))).thenReturn(origin);

        mockMvc.perform(post("/v1/origins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());

        Mockito.verify(originService, times(1)).saveOrigin(origin);
        Mockito.verify(originMapper, times(1)).mapToOrigin(any());
    }

    @Test
    public void testGetOrigin() throws Exception {
        //given
        Origin origin = new Origin(1L, "title", "author", 2000, "1234567890", null,0 );
        OriginDtoResponse originDtoResponse = new OriginDtoResponse(1L, "title", "author", 2000, "1234567890", null, 0);
        //when & then
        when(originService.getOrigin(anyLong())).thenReturn(Optional.of(origin));
        when(originMapper.mapToOriginDtoResponse(ArgumentMatchers.any(Origin.class))).thenReturn(originDtoResponse);

        mockMvc.perform(get("/v1/origins/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.author", is("author")))
                .andExpect(jsonPath("$.publishedYear", is(2000)))
                .andExpect(jsonPath("$.isbn", is("1234567890")));
    }

    @Test
    public void testGetOriginsList() throws Exception {
        //given
        List<Long> idsAList = new ArrayList<>();
        idsAList.add(3L);
        idsAList.add(4L);
        List<Long> idsBList = new ArrayList<>();
        idsBList.add(5L);
        idsBList.add(6L);
        OriginDtoResponse originA = new OriginDtoResponse(1L, "title1", "author1", 2001, "1234567890", idsAList, 0);
        OriginDtoResponse originB = new OriginDtoResponse(2L, "title2", "author2", 2002, "1234567890", idsBList, 0);

        List<OriginDtoResponse> originDtoResponseList = new ArrayList<>();
        originDtoResponseList.add(originA);
        originDtoResponseList.add(originB);

        //when & then
        when(originService.getAllOrigins()).thenReturn(new ArrayList<Origin>());
        when(originMapper.mapToOriginDtoResponseList(ArgumentMatchers.anyList())).thenReturn(originDtoResponseList);

        mockMvc.perform(get("/v1/origins"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("title1")))
                .andExpect(jsonPath("$[0].author", is("author1")))
                .andExpect(jsonPath("$[0].publishedYear", is(2001)))
                .andExpect(jsonPath("$[0].isbn", is("1234567890")))
                .andExpect(jsonPath("$[0].booksIds[0]", is(3)))
                .andExpect(jsonPath("$[0].booksIds[1]", is(4)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("title2")))
                .andExpect(jsonPath("$[1].author", is("author2")))
                .andExpect(jsonPath("$[1].publishedYear", is(2002)))
                .andExpect(jsonPath("$[1].isbn", is("1234567890")))
                .andExpect(jsonPath("$[1].booksIds[0]", is(5)))
                .andExpect(jsonPath("$[1].booksIds[1]", is(6)));
    }


    @Test
    public void testGetTitlesContainingPhrase() throws Exception{
        //given
        List<String> resultList = new ArrayList<>();
        resultList.add("Adventures");
        resultList.add("Treasures");

        //when & then
        when(originService.findTitlesByPhrase("urES")).thenReturn(resultList);

        mockMvc.perform(get("/v1/origins/urES/search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is("Adventures")))
                .andExpect(jsonPath("$[1]", is("Treasures")));
    }


    @Test
    public void testUpdateOrigin() throws Exception {
        //given:
        Origin origin = new Origin(1L, "title", "author", 2000, "1234567890", null, 0);
        OriginDtoRequest originDtoRequest = new OriginDtoRequest(2L, "title", "author", 2000, "1234567890", null, 0);

        Gson gson = new Gson();
        String requestBody = gson.toJson(originDtoRequest);

        //when:
        when(originMapper.mapToOrigin(any(OriginDtoRequest.class))).thenReturn(origin);
        //then:
        mockMvc.perform(put("/v1/origins")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());

        Mockito.verify(originService, times(1)).saveOrigin(origin);
    }


    @Test
    public void testGetOriginRating() throws Exception {
        //given:
        String hobbitIsbn = "0938768425";
        //when:
        when(goodreadsClient.getSingleRating(hobbitIsbn)).thenReturn("4.50");
        //then:
        mockMvc.perform(get("/v1/origins/rating/" + hobbitIsbn))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(4.50)));
    }


    @Test
    public void testDeleteOrigin() throws Exception{
        //given:
        //when & then:
        mockMvc.perform(delete("/v1/origins/1"))
                .andExpect(status().isOk());

        Mockito.verify(originService, times(1)).deleteOrigin(1L);
    }


    @Test
    public void testUpdateOriginRating() throws Exception {
        //given:
        OriginDtoRequest originDtoRequest = new OriginDtoRequest(1L, "Hobbit", "Tolkien", 2000, "0938768425", null, 4.5);
        Gson gson = new Gson();
        String requestBody = gson.toJson(originDtoRequest);
        //when & then:
        mockMvc.perform(put("/v1/origins/rating")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());

        Mockito.verify(originService, times(1)).updateOriginRating(any());
    }

    @Test
    public void testUpdateAllRatings() throws Exception {
        //given:
        //when & then:
        mockMvc.perform(put("/v1/origins/allRatings"))
                .andExpect(status().isOk());

        Mockito.verify(originService, times(1)).updateAllOriginsRatings();
    }

}
