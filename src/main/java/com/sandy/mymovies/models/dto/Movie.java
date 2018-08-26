package com.sandy.mymovies.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A bean containing the complete information on a Movie, including the Cast, Genres, and Tags.
 *
 * @see Movie
 * @see com.sandy.mymovies.models.dto.Tags
 * @see com.sandy.mymovies.models.dto.Genres
 * @see com.sandy.mymovies.models.dto.Cast
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

  private String imdbId;

  private String title;

  private Integer releaseYear;

  private String duration;

  private String rating;

  private String director;

  private String imageUrl;

  private String description;

  private Genres genre;

  private Tags tag;

  private Cast cast;

}
