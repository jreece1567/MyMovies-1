package com.sandy.mymovies.models.domain;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bean associating an episode with an imdbId and season.
 */
@Entity
@Table(indexes = {@Index(name = "IDX_IMDBID", columnList = "imdbId"),
    @Index(name = "IDX_SEASON", columnList = "season")})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chapter {

  /**
   * The imdbId of a show in which the episode appears.
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
  private String description;

}
