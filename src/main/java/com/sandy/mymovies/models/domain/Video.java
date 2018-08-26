package com.sandy.mymovies.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bean describing a movie. Note that several movie attributes (Cast,Genres,Tags) are maintained
 * separately.
 *
 * @see com.sandy.mymovies.models.domain.Actor
 * @see com.sandy.mymovies.models.domain.Genre
 * @see com.sandy.mymovies.models.domain.Tag
 */
@Entity
@Table(indexes = {@Index(name = "IDX_TITLE", columnList = "title"),
    @Index(name = "IDX_RELEASE_YEAR", columnList = "releaseYear"),
    @Index(name = "IDX_RATING", columnList = "rating"),
    @Index(name = "IDX_DIRECTOR", columnList = "director")})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {

  @Id
  private String imdbId;

  /**
   * The movie title.
   */
  @Column
  private String title;

  /**
   * The year that the movie was released.
   */
  @Column
  private Integer releaseYear;

  /**
   * The length of the movie in hours:minutes format.
   */
  @Column
  private String duration;

  /**
   * The MPAA rating of the movie.
   */
  @Column
  private String rating;

  /**
   * The name of the director.
   */
  @Column
  private String director;

  /**
   * The URL of the poster-image for the movie.
   */
  @Column
  private String imageUrl;

  /**
   * The description of the movie.
   */
  @Column
  private String description;

}
