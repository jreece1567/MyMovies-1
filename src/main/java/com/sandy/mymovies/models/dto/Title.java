package com.sandy.mymovies.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A minimal subset of Movie information.
 *
 * @see Movie
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Title {

  /**
   * The unique IMDB-id identifying this movie.
   */
  private String imdbId;

  /**
   * The movie title.
   */
  private String title;

  /**
   * The year that the movie was released.
   */
  private Integer releaseYear;

  /**
   * The URL of the poster-image for the movie.
   */
  private String imageUrl;

}
