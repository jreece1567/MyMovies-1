package com.sandy.mymovies.models.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bean associating an episode with an imdbId and season.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Episode {

  /**
   * The imdbId of a show in which the episode appears.
   */
  @Valid
  @NotNull
  @Pattern(regexp = "\\d{7}")
  private String imdbId;

  /**
   * The season of a show in which the episode appears.
   */
  @Valid
  @NotNull
  @Pattern(regexp = "\\d{1,2}")
  private String season;

  /**
   * The episode number (within a season) in which the episode appears.
   */
  @Valid
  @NotNull
  @Pattern(regexp = "\\d{1,2}")
  private String episodeNumber;

  /**
   * The title of the episode.
   */
  @Valid
  @NotNull
  @Size(min = 1, max = 255)
  private String title;

  /**
   * The description the episode.
   */
  @Valid
  @NotNull
  @Size(min = 1, max = 2048)
  private String description;

}
