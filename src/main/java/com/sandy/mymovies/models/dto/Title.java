package com.sandy.mymovies.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A minimal subset of Movie information.
 *
 * @see Movie
 */
@Data
@AllArgsConstructor
public class Title {

  private String imdbId;
  private String title;
  private Integer releaseYear;
  private String imageUrl;
}
