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
   * The length of the movie in hours:minutes format.
   */
  private String duration;

  /**
   * The MPAA rating of the movie.
   */
  private String rating;

  /**
   * The name of the director.
   */
  private String director;

  /**
   * The URL of the poster-image for the movie.
   */
  private String imageUrl;

  /**
   * The description of the movie.
   */
  private String description;

  /**
   * The list of genres associated with the movie.
   */
  private Genres genre;

  /**
   * The list of tags associated with the movie.
   */
  private Tags tag;

  /**
   * The list of actor-names associated with the movie.
   */
  private Cast cast;

}
