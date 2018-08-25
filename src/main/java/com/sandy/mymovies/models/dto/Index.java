package com.sandy.mymovies.models.dto;

public enum Index {
    ALL("all"),
    TAG("tag"),
    ACTOR("actor"),
    GENRE("genre"),
    DIRECTOR("director"),
    RATING("rating"),
    TITLE("title"),
    YEAR("year");

    private String value;

    private Index(String value) {
        this.value = value;
    }
}
