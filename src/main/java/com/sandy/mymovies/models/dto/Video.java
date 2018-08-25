package com.sandy.mymovies.models.dto;

import lombok.Data;

@Data
public class Video {

    private String imdbId;

    private String title;

    private Integer releaseYear;

    private String duration;

    private String rating;

    private String director;

    private String imageUrl;

    private String description;

    private Genre genre;

    private Tag tag;

    private Cast cast;

}
