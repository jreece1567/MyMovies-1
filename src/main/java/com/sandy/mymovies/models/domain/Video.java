package com.sandy.mymovies.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Bean describing a movie. Note that several movie attributes (Cast,Genres,Tags) are maintained
 * separately.
 *
 * @see com.sandy.mymovies.models.dto.Movie
 * @see com.sandy.mymovies.models.domain.Actor
 * @see com.sandy.mymovies.models.domain.Genre
 * @see com.sandy.mymovies.models.domain.Tag
 */
@Entity
@Table(indexes = {@Index(name = "IDX_VIDEO_TITLE", columnList = "title"),
    @Index(name = "IDX_VIDEO_RELEASE_YEAR", columnList = "releaseYear"),
    @Index(name = "IDX_VIDEO_RATING", columnList = "rating"),
    @Index(name = "IDX_VIDEO_DIRECTOR", columnList = "director")})
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Video extends Timestamped {

  /**
   * The unique IMDB-id identifying this movie.
   */
  @Id
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
  @Column(length = 2048)
  private String description;

}
