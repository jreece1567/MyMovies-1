package com.sandy.mymovies.models.dto;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A bean containing the complete information on a Movie, including the Cast, Genres, and Tags.
 *
 * @see com.sandy.mymovies.models.domain.Video
 * @see com.sandy.mymovies.models.dto.Tags
 * @see com.sandy.mymovies.models.dto.Genres
 * @see com.sandy.mymovies.models.dto.Cast
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {

  /**
   * The unique IMDB-id identifying this movie.
   */
  @Valid
  @NotNull
  @Pattern(regexp = "\\d{7}")
  private String imdbId;

  /**
   * The movie title.
   */
  @Valid
  @NotNull
  @Size(min = 1, max = 255)
  private String title;

  /**
   * The year that the movie was released.
   */
  @Valid
  @NotNull
  @Min(1900)
  @Max(2099)
  private Integer releaseYear;

  /**
   * The length of the movie in hours:minutes format.
   */
  @Valid
  @NotNull
  @Pattern(regexp = "[0-9]:[0-5][0-9]")
  private String duration;

  /**
   * The MPAA rating of the movie.
   */
  @Valid
  @NotNull
  private String rating;

  /**
   * The name of the director.
   */
  @Valid
  @NotNull
  @Size(min = 1, max = 255)
  private String director;

  /**
   * The URL of the poster-image for the movie.
   */
  @Valid
  @NotNull
  @Size(min = 1, max = 255)
  private String imageUrl;

  /**
   * The description of the movie.
   */
  @Valid
  @NotNull
  @Size(min = 1, max = 2048)
  private String description;

  /**
   * The list of genres associated with the movie.
   */
  @Valid
  @NotNull
  @NotEmpty
  private Genres genres;

  /**
   * The list of tags associated with the movie.
   */
  @Valid
  @NotNull
  private Tags tags;

  /**
   * The list of actor-names associated with the movie.
   */
  @Valid
  @NotNull
  @NotEmpty
  private Cast cast;

}
