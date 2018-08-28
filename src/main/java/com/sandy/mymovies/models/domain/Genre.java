package com.sandy.mymovies.models.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bean associating a genre with an imdbId.
 */
@Entity
@Table(indexes = {@Index(name = "IDX_GENRE_GENRE", columnList = "genre"),
    @Index(name = "IDX_GENRE_IMDBID", columnList = "imdbId")})
@Data
@NoArgsConstructor
public class Genre {

  /**
   * The unique row-id.
   */
  @Id
  @GeneratedValue
  private Long id;

  /**
   * The name of the genre.
   */
  private String genre;

  /**
   * The imdbId of a movie associated with this genre.
   */
  private String imdbId;

  /**
   * All-args constructor (can't use lombok since this bean has an auto-generated 'id' field).
   *
   * @param genre The genre name.
   * @param imdbId The imdbId of a movie associated with this genre.
   */
  public Genre(final String genre, final String imdbId) {
    this.genre = genre;
    this.imdbId = imdbId;
  }

}
