package com.sandy.mymovies.models.domain;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bean associating an actor-name with an imdbId.
 */
@Entity
@Table(indexes = {@Index(name = "IDX_NAME", columnList = "name"),
    @Index(name = "IDX_IMDBID", columnList = "imdbId")})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Actor {

  /**
   * The actor name.
   */
  private String name;

  /**
   * The imdbId of a movie in which the actor appears.
   */
  private String imdbId;

}
