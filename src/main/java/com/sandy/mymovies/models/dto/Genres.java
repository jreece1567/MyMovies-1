package com.sandy.mymovies.models.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of genres.
 */
@SuppressWarnings("serial")
public class Genres extends ArrayList<String> {

  public Genres() {
    super();
  }

  public Genres(List<String> other) {
    super(other);
  }

}
