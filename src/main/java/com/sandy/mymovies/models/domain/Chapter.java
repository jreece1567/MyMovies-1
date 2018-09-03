package com.sandy.mymovies.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Bean associating an episode with an imdbId and season.
 */
@Entity
@Table(indexes = {@Index(name = "IDX_CHAPTER_IMDBID", columnList = "imdbId"),
    @Index(name = "IDX_CHAPTER_SEASON", columnList = "season")})
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Chapter extends Timestamped {

  /**
   * The unique row-id.
   */
  @Id
  @GeneratedValue
  private Long id;

  /**
   * The unique IMDB-id identifying this movie.
   */
  private String imdbId;

  /**
   * The season of a show in which the episode appears.
   */
  private Integer season;

  /**
   * The episode number (within a season) in which the episode appears.
   */
  private Integer episodeNumber;

  /**
   * The title of the episode.
   */
  private String title;

  /**
   * The description the episode.
   */
  @Column(length = 2048)
  private String description;

  /**
   * All-args constructor (can't use lombok since this bean has an auto-generated 'id' field).
   *
   * @param imdbId The unique IMDB-id identifying this movie.
   * @param season The season of a show in which the episode appears.
   * @param episodeNumber The episode number (within a season) in which the episode appears.
   * @param title The title of the episode.
   * @param description The description the episode.
   */
  public Chapter(final String imdbId, final Integer season, final Integer episodeNumber,
      final String title, final String description) {
    this.imdbId = imdbId;
    this.season = season;
    this.episodeNumber = episodeNumber;
    this.title = title;
    this.description = description;
  }

}
