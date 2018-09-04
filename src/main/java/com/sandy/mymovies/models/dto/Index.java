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

  /**
   * Convert a string value to the associated enum instance, if possible.
   *
   * @param value the string value.
   * @return the enum constant with the specified value.
   * @throws IllegalArgumentException if this enum type has no constant with the specified value.
   */
  public static Index fromValue(final String value) {

    for (Index index : values()) {
      if (index.getValue().equals(value)) {
        return index;
      }
    }
    throw new IllegalArgumentException(
        "No enum constant in " + Index.class.getName() + " for value '" + value + "'");
  }

  /**
   * Return the value of this enum constant.
   *
   * @return the value.
   */
  public String getValue() {
    return value;
  }
}
