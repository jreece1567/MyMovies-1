package com.sandy.mymovies.models.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of actor-names.
 */
@SuppressWarnings("serial")
public class Cast extends ArrayList<String> {

  public Cast() {
    super();
  }

  public Cast(List<String> other) {
    super(other);
  }
}

