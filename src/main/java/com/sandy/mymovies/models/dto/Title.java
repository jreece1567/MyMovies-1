package com.sandy.mymovies.models.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A minimal subset of Movie information.
 *
 * @see Movie
 */
@Data
@AllArgsConstructor
public class Title implements Serializable {

    private String imdbId;
    private String title;
    private Integer releaseYear;
    private String imageUrl;
}
