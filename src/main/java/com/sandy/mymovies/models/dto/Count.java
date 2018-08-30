package com.sandy.mymovies.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bean containing a value, and a count of rows for that value.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Count {

  /**
   * The value being counted.
   */
  private String value;

  /**
   * The count of values.
   */
  private Integer count;
}
