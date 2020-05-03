package com.sandy.mymovies.models.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of tag names.
 */
@SuppressWarnings("serial")
public class Tags extends ArrayList<String> {

  public Tags() {
    super();
  }

  public Tags(List<String> other) {
    super(other);
  }
}
