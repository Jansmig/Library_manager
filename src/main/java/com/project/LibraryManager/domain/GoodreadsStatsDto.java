package com.project.LibraryManager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoodreadsStatsDto {

    private int id;
    private String isbn;
    private String isbn13;
    private int ratings_count;
    private int reviews_count;
    private int text_reviews_count;
    private int work_ratings_count;
    private int work_reviews_count;
    private int work_text_reviews_count;

    @JsonProperty("average_rating")
    private String averageRating;

}
