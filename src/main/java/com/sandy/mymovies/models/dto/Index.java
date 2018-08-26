package com.sandy.mymovies.models.dto;

/**
 * The defined indices for the MyMovies app.
 */
public enum Index {

  ALL("all"), TAG("tag"), ACTOR("actor"), GENRE("genre"), DIRECTOR("director"), RATING(
      "rating"), TITLE("title"), YEAR("year");

  private String value;

  private Index(final String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

}
