package com.sandy.mymovies.models.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;

/**
 * Bean associating an actor-name with an imdbId.
 */
@Entity
@Table(indexes = {@Index(name = "IDX_ACTOR_NAME", columnList = "name"),
    @Index(name = "IDX_ACTOR_IMDBID", columnList = "imdbId")})
@Data
@Builder
public class Actor {

  /**
   * The unique row-id.
   */
  @Id
  @GeneratedValue
  private Long id;

  /**
   * The actor name.
   */
  private String name;

  /**
   * The imdbId of a movie in which the actor appears.
   */
  private String imdbId;

  /**
   * All-args constructor (can't use lombok since this bean has an auto-generated 'id' field).
   *
   * @param name The actor name.
   * @param imdbId The imdbId of a movie in which the actor appears.
   */
  public Actor(final String name, final String imdbId) {
    this.name = name;
    this.imdbId = imdbId;
  }
}
