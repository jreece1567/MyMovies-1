package com.sandy.mymovies.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bean associating an episode with an imdbId and season.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Episode {

  /**
   * The imdbId of a show in which the episode appears.
   */
  private String imdbId;

  /**
   * The season of a show in which the episode appears.
   */
  private String season;

  /**
   * The episode number (within a season) in which the episode appears.
   */
  private String episodeNumber;

  /**
   * The title of the episode.
   */
  private String title;

  /**
   * The description the episode.
   */
  private String description;

}
