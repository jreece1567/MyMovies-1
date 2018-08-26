package com.sandy.mymovies.models.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;

/**
 * Bean associating an episode with an imdbId and season.
 */
@Entity
@Table(indexes = {@javax.persistence.Index(name = "IDX_IMDBID", columnList = "imdbId"),
    @Index(name = "IDX_SEASON", columnList = "season")})
@Data
public class Chapter implements Serializable {

  @Id
  @GeneratedValue
  private int id;

  /**
   * The imdbId of a show in which the episode appears.
   */
  @Column
  private String imdbId;

  /**
   * The season of a show in which the episode appears.
   */
  @Column
  private Integer season;

  /**
   * The episode number (within a season) in which the episode appears.
   */
  @Column
  private Integer episodeNumber;

  /**
   * The title of the episode.
   */
  @Column
  private String title;

  /**
   * The description the episode.
   */
  @Column
  private String description;

  public Chapter(final String imdbId, final Integer season, final Integer episodeNumber,
      final String title, final String description) {
    this.imdbId = imdbId;
    this.season = season;
    this.episodeNumber = episodeNumber;
    this.title = title;
    this.description = description;
  }
  // @todo - consider compound key? imdbId, season, episode number, or, use custom SQL (SELECT *
  // FROM Episode WHERE imdbId=? AND season=? ORDER BY episodeNumber)
}
