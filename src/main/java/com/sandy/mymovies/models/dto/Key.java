package com.sandy.mymovies.models.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * An index-key value, and a list of all associated imdbIds.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Key {

  /**
   * The key associated with the list of imdbIds
   */
  private String key;

  /**
   * The list of imdbIds
   */
  private List<String> ids;
}
