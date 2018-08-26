package com.sandy.mymovies.models.domain;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bean associating a genre with an imdbId.
 */
@Entity
@Table(indexes = {@Index(name = "IDX_GENRE", columnList = "genre"),
    @Index(name = "IDX_IMDBID", columnList = "imdbId")})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre {

  /**
   * The name of the genre.
   */
  private String genre;

  /**
   * The imdbId of a movie associated with this genre.
   */
  private String imdbId;

}
